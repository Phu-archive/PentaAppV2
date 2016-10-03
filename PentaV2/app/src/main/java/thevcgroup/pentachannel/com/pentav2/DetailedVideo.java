package thevcgroup.pentachannel.com.pentav2;

public class DetailedVideo {
    String url_video_img;
    String url_channel_img;
    String name_video;
    String name_channel;
    String created_time;

    public DetailedVideo(String url_video_img,String url_channel_img,String name_video,String name_channel,String created_time){
        this.url_video_img = url_video_img;
        this.url_channel_img =url_channel_img;
        this.name_video = name_video;
        this.name_channel = name_channel;
        this.created_time = created_time;
    }

    public String getCreated_time() {
        return created_time;
    }

    public String getName_channel() {
        return name_channel;
    }

    public String getName_video() {
        return name_video;
    }

    public String getUrl_channel_img() {
        return url_channel_img;
    }

    public String getUrl_video_img() {
        return url_video_img;
    }
}
