public class Text {
    String text;
    Point origin;
    int timeout;
    int maxTimeOut = 300;
    Text(String text, Point ori){
        origin = ori;
        this.text = text;
    }
}
