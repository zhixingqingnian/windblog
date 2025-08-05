package com.zhixing.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加标签")
public class BatchAddTagReqVO {

    @NotEmpty(message = "标签集合不能为空")
    @ApiModelProperty(value = "标签集合")
    private List<String> tags;
}
