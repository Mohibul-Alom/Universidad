package es.urjc.dadproject.youwatch;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class videoConversionService
{
    private static final Logger log = LoggerFactory.getLogger(homeController.class);

    public void convertHLS (Path videoPath, String videoID) throws IOException
    {
        convert1080HLS(videoPath,videoID);
        convert720HLS(videoPath,videoID);
        convert480HLS(videoPath,videoID);
    }

    public void createThumbnail(Path videoPath, String videoID) throws IOException {
        FFmpeg  ffmpeg = new FFmpeg("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffprobe");

        log.error("Iniciando creacion miniatura");
        String path;
        path = ".\\videos\\" + videoID + "\\thumbnail.gif";

        /*
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(String.valueOf(videoPath))
                .overrideOutputFiles(true)
                //.addOutput(".\\videos\\720p.m3u8")
                .addOutput(path)

                .addExtraArgs("-ss","00:00:01.000")
                .addExtraArgs("-vframes","1")

                .done();
        */

        /*
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(String.valueOf(videoPath))
                .overrideOutputFiles(true)
                //.addOutput(".\\videos\\720p.m3u8")
                .addOutput(path)

                .addExtraArgs("-ss","3")
                .addExtraArgs("-vf","\"select = gt(scene\\, 0.8)\"")
                .addExtraArgs("-frames:v","10")
                .addExtraArgs("-vsync","vfr")
                .addExtraArgs("-vf","\"fps=fps=1/200\"")
                .addExtraArgs("-y","out%03d.png")


                .done();
        */

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(String.valueOf(videoPath))
                .overrideOutputFiles(true)
                //.addOutput(".\\videos\\720p.m3u8")
                .addOutput(path)

                .addExtraArgs("-ss","00:00:01")
                .addExtraArgs("-t","00:00:03")
                .addExtraArgs("-vf","\"fps=3,scale=400:-1:flags=lanczos,split[s0][s1];[s0]palettegen[p];[s1][p]paletteuse\"")
                .addExtraArgs("-loop","0")
                //.addExtraArgs("-vsync","vfr")
                //.addExtraArgs("-vf","\"fps=fps=1/200\"")
                //.addExtraArgs("-y","out%03d.png")


                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        // Run a one-pass encode
        executor.createJob(builder).run();

        // Or run a two-pass encode (which is better quality at the cost of being slower)
        //executor.createTwoPassJob(builder).run();

        log.error("Creacion miniatura finalizada");
    }

    private void convert1080HLS (Path videoPath, String videoID) throws IOException
    {
        FFmpeg  ffmpeg = new FFmpeg("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffprobe");

        log.error("Iniciando conversion 1080p");
        String path;
        path = ".\\videos\\" + videoID + "\\1080p.m3u8";
        String path2 = ".\\videos\\" + videoID + "\\1080p_%03d.ts";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(String.valueOf(videoPath))
                .overrideOutputFiles(true)
                //.addOutput(".\\videos\\720p.m3u8")
                .addOutput(path)

                //.addExtraArgs("-i" ,videoPath.toString())
                //.addExtraArgs("-vf" ,"scale=w=1280:h=720:force_original_aspect_ratio=decrease")

                .addExtraArgs("-vf" ,"scale=w=1920:h=1080:force_original_aspect_ratio=decrease")
                .addExtraArgs("-c:a","aac")
                .addExtraArgs("-ar","48000")
                .addExtraArgs("-b:a","192k")
                .addExtraArgs("-c:v","h264")
                .addExtraArgs("-profile:v","main")
                .addExtraArgs("-crf","20")
                .addExtraArgs("-g","48")
                .addExtraArgs("-keyint_min","48")
                .addExtraArgs("-sc_threshold","0")
                .addExtraArgs("-b:v","5000k")
                .addExtraArgs("-maxrate","5350k")
                .addExtraArgs("-bufsize","7500k")
                .addExtraArgs("-hls_time","4")
                .addExtraArgs("-hls_playlist_type","vod")
                //.addExtraArgs("-hls_segment_filename",".\\videos\\720p_%03d.ts")
                .addExtraArgs("-hls_segment_filename",path2)

                .addExtraArgs("-vf","\"crop=trunc(iw/2)*2:trunc(ih/2)*2\"")

                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        // Run a one-pass encode
        executor.createJob(builder).run();

        // Or run a two-pass encode (which is better quality at the cost of being slower)
        //executor.createTwoPassJob(builder).run();

        log.error("Conversion finalizada 1080p");
    }

    private void convert720HLS (Path videoPath, String videoID) throws IOException
    {
        FFmpeg  ffmpeg = new FFmpeg("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffprobe");

        log.error("Iniciando conversion 720p");
        String path;
        path = ".\\videos\\" + videoID + "\\720p.m3u8";
        String path2 = ".\\videos\\" + videoID + "\\720p_%03d.ts";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(String.valueOf(videoPath))
                .overrideOutputFiles(true)
                //.addOutput(".\\videos\\720p.m3u8")
                .addOutput(path)

                //.addExtraArgs("-i" ,videoPath.toString())
                //.addExtraArgs("-vf" ,"scale=w=1280:h=720:force_original_aspect_ratio=decrease")

                .addExtraArgs("-vf" ,"scale=w=1280:h=720:force_original_aspect_ratio=decrease")
                .addExtraArgs("-c:a","aac")
                .addExtraArgs("-ar","48000")
                .addExtraArgs("-b:a","128k")
                .addExtraArgs("-c:v","h264")
                .addExtraArgs("-profile:v","main")
                .addExtraArgs("-crf","20")
                .addExtraArgs("-g","48")
                .addExtraArgs("-keyint_min","48")
                .addExtraArgs("-sc_threshold","0")
                .addExtraArgs("-b:v","2800k")
                .addExtraArgs("-maxrate","2996k")
                .addExtraArgs("-bufsize","4200k")
                .addExtraArgs("-hls_time","4")
                .addExtraArgs("-hls_playlist_type","vod")
                //.addExtraArgs("-hls_segment_filename",".\\videos\\720p_%03d.ts")
                .addExtraArgs("-hls_segment_filename",path2)

                .addExtraArgs("-vf","\"crop=trunc(iw/2)*2:trunc(ih/2)*2\"")

                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        // Run a one-pass encode
        executor.createJob(builder).run();

        // Or run a two-pass encode (which is better quality at the cost of being slower)
        //executor.createTwoPassJob(builder).run();

        log.error("Conversion finalizada 720p");
    }

    private void convert480HLS (Path videoPath, String videoID) throws IOException
    {
        FFmpeg  ffmpeg = new FFmpeg("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\Users\\Diego\\Downloads\\ffmpeg-N-101309-g10341743d2-win64-gpl\\ffmpeg-N-101309-g10341743d2-win64-gpl\\bin\\ffprobe");

        log.error("Iniciando conversion 480p");
        String path;
        path = ".\\videos\\" + videoID + "\\480p.m3u8";
        String path2 = ".\\videos\\" + videoID + "\\480p_%03d.ts";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(String.valueOf(videoPath))
                .overrideOutputFiles(true)
                //.addOutput(".\\videos\\720p.m3u8")
                .addOutput(path)

                //.addExtraArgs("-i" ,videoPath.toString())
                //.addExtraArgs("-vf" ,"scale=w=1280:h=720:force_original_aspect_ratio=decrease")

                .addExtraArgs("-vf" ,"scale=w=842:h=480:force_original_aspect_ratio=decrease")
                .addExtraArgs("-c:a","aac")
                .addExtraArgs("-ar","48000")
                .addExtraArgs("-b:a","128k")
                .addExtraArgs("-c:v","h264")
                .addExtraArgs("-profile:v","main")
                .addExtraArgs("-crf","20")
                .addExtraArgs("-g","48")
                .addExtraArgs("-keyint_min","48")
                .addExtraArgs("-sc_threshold","0")
                .addExtraArgs("-b:v","1400k")
                .addExtraArgs("-maxrate","1498k")
                .addExtraArgs("-bufsize","2100k")
                .addExtraArgs("-hls_time","4")
                .addExtraArgs("-hls_playlist_type","vod")
                //.addExtraArgs("-hls_segment_filename",".\\videos\\720p_%03d.ts")
                .addExtraArgs("-hls_segment_filename",path2)

                .addExtraArgs("-vf","\"crop=trunc(iw/2)*2:trunc(ih/2)*2\"")

                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        // Run a one-pass encode
        executor.createJob(builder).run();

        // Or run a two-pass encode (which is better quality at the cost of being slower)
        //executor.createTwoPassJob(builder).run();

        log.error("Conversion finalizada 480p");
    }


}
