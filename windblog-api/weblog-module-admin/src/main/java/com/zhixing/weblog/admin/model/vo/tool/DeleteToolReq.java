package com.zhixing.weblog.admin.model.vo.tool;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "删除工具 VO")
public class DeleteToolReq {
    @NotNull(message = "工具id不能为空")
    private Long id;
}
