public class constant {
    public String getDate{
        String date=null;
        DateTimeFormatter dtf=null;
        if (Build.VERSION.SDK.INT>=Build.VERSION_CODES.0){
            dtf=DateTimeFormatter.ofPattern("yyy/mm/dd HH:mm:ss");
        }
        LocalDateTime now=null;
        if (Build.VERSION.SDK.INT>=Build.VERSION_CODES.0){
            now=LocalDateTime.now();
        }
        return date;
    }
}
