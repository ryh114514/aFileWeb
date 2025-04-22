package ryh.thesrcfinder.pojo;

import lombok.Data;

@Data
public class result {
    private String msg;
    private Object data;
    private Integer error_num;
    private Integer total;
    public result(Integer code, Object data, String msg,Integer total) {
        this.error_num = code;
        this.data = data;
        this.msg = msg;
        this.total=total;
    }
    static public result error(String msg){
        return new result(1,null,msg,null);
    }
    static public result success(Object data,Integer total){
        return new result(0,data,"success",total);
    }
}
