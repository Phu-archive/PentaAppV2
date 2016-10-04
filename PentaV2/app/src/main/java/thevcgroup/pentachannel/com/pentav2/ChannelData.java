package thevcgroup.pentachannel.com.pentav2;

public class ChannelData {
    String ch_img;
    String ch_name;
    String follower;
    String id;

    public ChannelData(String channelImg,String channelName,String NumberFollower, String ID){
        ch_img = channelImg;
        ch_name = channelName;
        follower = NumberFollower;
        id = ID;
    }

    public String getCh_img() {
        return ch_img;
    }

    public String getCh_name() {
        return ch_name;
    }

    public String getFollower() {
        return follower;
    }

    public String getId() {
        return id;
    }
}
