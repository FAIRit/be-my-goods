package com.slyszmarta.bemygoods.security.registration;

import com.slyszmarta.bemygoods.exceptions.InvalidOldPasswordException;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@RestController
@Api(value = "Registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final ApplicationUserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final JavaMailSender mailSender;
    private final Environment env;
    private final SecurityUserService securityService;

    @Qualifier("messageSource")
    private final MessageSource messages;

    @PostMapping("/user/registration")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Register user account", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registration succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public GenericResponse registerUserAccount(@ApiParam(value = "User account information", required = true) @Valid ApplicationUserDto accountDto, @ApiIgnore HttpServletRequest request) throws ValidationException {
        log.debug("Registering user account with information: {}", accountDto);
        ApplicationUser registered = createUserAccount(accountDto);
        if (registered == null) {
            throw new ValidationException(String.valueOf(accountDto.getId().longValue()));
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    @GetMapping("/registrationConfirm")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Confirm user registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Confirmation succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public String confirmRegistration(@ApiIgnore Locale locale, @ApiIgnore Model model, @ApiParam(value = "Registration token", required = true) @RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        ApplicationUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }

    @GetMapping("/user/resendRegistrationToken")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Resend registration token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token resending succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public GenericResponse resendRegistrationToken(@ApiIgnore HttpServletRequest request, @ApiParam(value = "Existing registration token", required = true) @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        ApplicationUser user = userService.getUser(newToken.getToken());
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
        mailSender.send(email);
        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    @PostMapping("/user/resetPassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Reset password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password reset succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public GenericResponse resetPassword(@ApiIgnore HttpServletRequest request, @ApiParam(value = "User email", required = true) @RequestParam("email") String userEmail) {
        ApplicationUser user = userService.getExistingUserByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    @GetMapping("/user/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Change password page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change password page loading succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public String showChangePasswordPage(@ApiIgnore Locale locale, @ApiIgnore Model model, @ApiIgnore @AuthenticationPrincipal ApplicationUser user, @ApiParam(value = "Password reset token", required = true) @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(user.getId(), token);
        if (result != null) {
            model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    @PostMapping("/user/savePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password save succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public GenericResponse savePassword(@ApiIgnore Locale locale, @ApiIgnore @AuthenticationPrincipal ApplicationUser user, @ApiParam(value = "Password object to save", required = true) @Valid PasswordDto passwordDto) {
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
    }

    @GetMapping("/user/registration")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Show registration form")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registration form loading succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public String showRegistrationForm(@ApiIgnore WebRequest request, @ApiIgnore Model model) {
        ApplicationUserDto userDto = new ApplicationUserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/user/updatePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Update password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password update succeed."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public GenericResponse changeUserPassword(@ApiIgnore Locale locale, @ApiParam(value = "New password", required = true) @RequestParam("password") String password, @ApiParam(value = "Old password", required = true) @RequestParam("oldpassword") String oldPassword) {
        ApplicationUser user = userService.getExistingUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    private ApplicationUser createUserAccount(ApplicationUserDto accountDto) {
        ApplicationUser registered = null;
        try {
            registered = userService.create(accountDto);
        } catch (ValidationException e) {
            return null;
        }
        return registered;
    }

    private SimpleMailMessage constructResendVerificationTokenEmail(String contextPath, Locale locale, VerificationToken newToken, ApplicationUser user) {
        String confirmationUrl = contextPath + "/regitrationConfirm.html?token=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, locale);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " rn" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        email.setTo(user.getEmail());
        return email;
    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, ApplicationUser user) {
        String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, ApplicationUser user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
