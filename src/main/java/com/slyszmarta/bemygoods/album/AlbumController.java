package com.slyszmarta.bemygoods.album;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/myprofile/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public String getAlbum(Model model){
        model.addAttribute("albumList", albumService.findAll());
        return "albums";
    }

    @PostMapping
    public String addAlbum(@Valid @RequestBody AlbumDto dto){
        albumService.create(dto);
        return "redirect:albums";
    }
}
