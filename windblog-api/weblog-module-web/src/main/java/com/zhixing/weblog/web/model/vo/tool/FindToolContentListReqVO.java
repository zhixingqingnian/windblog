package com.zhixing.weblog.web.model.vo.tool;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "查询工具内容列表 VO")
public class FindToolContentListReqVO {
    @NotNull(message = "工具id不能为空")
    private Long id;
}
