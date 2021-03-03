package es.urjc.dadproject.youwatch;


import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class videoController
{
    private static final Logger log = LoggerFactory.getLogger(homeController.class);
    private static final Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),"videos");

    private videoConversionService videoConversionService;

    public videoController(videoConversionService videoConversionService)
    {
        this.videoConversionService = videoConversionService;
    }

    /*Direccion principal que carga la web con el reproductor*/
    @GetMapping("/video")
    public String watch(Model model, @RequestParam String id) throws MalformedURLException {
        String videoName = id;
        //log.info("Video Cargado");
        //log.info(String.valueOf(IMAGES_FOLDER));

        model.addAttribute("name","patata");
        model.addAttribute("id",id);
        model.addAttribute("videoName",videoName);
        model.addAttribute("videoNamePlayer",videoName);
        model.addAttribute("download",false);
        model.addAttribute("session",false);
        return "video";
    }

    /*Direccion que carga la lista de reproduccion o los ficheros de video*/
    @GetMapping("/video_url/{id}/{p1}")
    public ResponseEntity<Object> video_url (@PathVariable String id, @PathVariable String p1) throws MalformedURLException
    {

        //log.error(p1);

        //Si es list servimos la lista
        if(p1.equals("list720"))
        {
            String url;
            url = "videos\\" + id + "\\720p.m3u8";

            Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);

            //Path videoPath = VIDEOS_FOLDER.resolve(url);
            Resource video = new UrlResource(VIDEOS_FOLDER.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/m3u8")
                    .body(video);
        }

        else if(p1.equals("list1080"))
        {
            String url;
            url = "videos\\" + id + "\\1080p.m3u8";

            Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);

            //Path videoPath = VIDEOS_FOLDER.resolve(url);
            Resource video = new UrlResource(VIDEOS_FOLDER.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/m3u8")
                    .body(video);
        }

        else if(p1.equals("list480"))
        {
            String url;
            url = "videos\\" + id + "\\480p.m3u8";

            Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);

            //Path videoPath = VIDEOS_FOLDER.resolve(url);
            Resource video = new UrlResource(VIDEOS_FOLDER.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/m3u8")
                    .body(video);
        }

        else //Si no servimos el fichero de video que toque
        {
            String url;
            url = "videos\\" + id + "\\" + p1;

            Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);

            //Path videoPath = VIDEOS_FOLDER.resolve(url);
            Resource video = new UrlResource(VIDEOS_FOLDER.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/m3u8")
                    .body(video);
        }
    }


    /*Direccion que carga la miniatura*/
    @GetMapping("/videoThumb/{id}")
    public ResponseEntity<Object> video_thumb (@PathVariable String id) throws MalformedURLException
    {

            String url;
            url = "videos\\" + id + "\\thumbnail.gif";

            Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);

            //Path videoPath = VIDEOS_FOLDER.resolve(url);
            Resource video = new UrlResource(VIDEOS_FOLDER.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/gif")
                    .body(video);

    }

    /*Direccion que de descarga del video*/
    @GetMapping("/videoDownload/{id}")
    public ResponseEntity<Object> video_download (@PathVariable String id) throws MalformedURLException
    {

        String url;
        url = "videos\\" + id + "\\Original.mkv";

        Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);

        //Path videoPath = VIDEOS_FOLDER.resolve(url);
        Resource video = new UrlResource(VIDEOS_FOLDER.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/gif")
                .body(video);

    }

    /*Direccion que carga la pagina de subir video*/
    @GetMapping("/upload")
    public String upload(Model model)
    {
        model.addAttribute("name","patata");
        model.addAttribute("session",false);

        return "uploadVideo";
    }

    /*Direccion para subir el video*/
    @PostMapping("/uploadVideo")
    public String uploadVideo(@RequestParam String imageName,
                              @RequestParam MultipartFile image ) throws IOException
    {
        log.error(imageName);

        String url;
        url = "videos\\" + imageName;

        Path VIDEOS_FOLDER = Paths.get(System.getProperty("user.dir"),url);
        Files.createDirectories(VIDEOS_FOLDER);
        //Path imagePath = VIDEOS_FOLDER.resolve(image.getOriginalFilename());
        Path imagePath = VIDEOS_FOLDER.resolve("Original." + FilenameUtils.getExtension(image.getOriginalFilename()));
        image.transferTo(imagePath);

        //Files.createDirectories(VIDEOS_FOLDER);
        //Path imagePath = VIDEOS_FOLDER.resolve(image.getOriginalFilename());
        //image.transferTo(imagePath);

        videoConversionService.convertHLS(imagePath,imageName);
        videoConversionService.createThumbnail(imagePath,imageName);

        return "redirect:/";
    }

}