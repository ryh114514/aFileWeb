package ryh.thesrcfinder.mapper;

import org.apache.ibatis.annotations.Mapper;
import ryh.thesrcfinder.pojo.information;

import java.util.List;
@Mapper
public interface srcSelecter {
    List<information> select(String like,Double size,String type,Integer pagesize);
}
