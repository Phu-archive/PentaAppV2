package thevcgroup.pentachannel.com.pentav2;

public class ChannelDetailed {
    String videoImg;
    String videoName;
    String videoType;
    String videoDetail;
    String videoID;

    public ChannelDetailed(String ImgVideo, String nameVideo, String typeVideo,String detailVideo,String IDvideo){
        videoImg = ImgVideo;
        videoName = nameVideo;
        videoType = typeVideo;
        videoDetail = detailVideo;
        videoID = IDvideo;
    }

    public String getVideoDetail() {
        return videoDetail;
    }

    public String getVideoID() {
        return videoID;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoType() {
        return videoType;
    }
}
