package ryh.thesrcfinder.mapper;

import org.apache.ibatis.annotations.Mapper;
import ryh.thesrcfinder.pojo.information;

import java.util.List;

@Mapper
public interface DBreflasher {
    void reflash(List<information> data);
    void delete();
}
