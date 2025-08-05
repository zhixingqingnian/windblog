package com.zhixing.weblog.admin.model.vo.user;

import com.zhixing.weblog.comm.model.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserPageReqVO extends BasePageQuery {
    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "开始日期")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束日期")
    private LocalDate endDate;

}
