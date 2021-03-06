package igloosec.monitor.service;

import igloosec.monitor.mapper.CostMapper;
import igloosec.monitor.vo.CostVo;
import igloosec.monitor.vo.ResourceVo;
import igloosec.monitor.vo.UsageVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostService {
    public final CostMapper mapper;

    public CostService(CostMapper mapper) {
        this.mapper = mapper;

    }

    public List<CostVo> selectCostList( CostVo param ) {
        return mapper.selectCostList(param);
    }


}

