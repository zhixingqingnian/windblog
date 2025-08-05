package com.zhixing.weblog.web.markdown.renderer;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.commonmark.ext.image.attributes.ImageAttributes;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;


public class ImageNodeRenderer implements NodeRenderer {
    private final HtmlWriter htmlWriter;
    // 图片宽
    private final static String KEY_WIDTH = "width";
    // 图片高
    private final static String KEY_HEIGHT = "height";

    public ImageNodeRenderer(HtmlNodeRendererContext context) {
        this.htmlWriter = context.getWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        // 指定想要自定义渲染的节点，这里指定为图片
        return Collections.singleton(Image.class);
    }

    @Override
    public void render(Node node) {
        Image image = (Image) node;
        htmlWriter.line();
        // 图片链接
        String imageUrl = image.getDestination();
        // 图片标题
        String imageTitle = image.getTitle();

        // 拼接 HTML 结构
        StringBuilder sb = new StringBuilder("<img src=\">");
        sb.append(imageUrl);
        sb.append("\"");

        // 添加 title alt 属性
        if (StringUtils.isNotBlank(imageTitle)) {
            sb.append(String.format(" title=\"%s\" alt=\"%s\"", imageTitle, imageTitle));
        }

        // 图片宽高
        Node lastChild = node.getLastChild();
        if (Objects.nonNull(lastChild) && lastChild instanceof ImageAttributes) {
            ImageAttributes imageAttributes = (ImageAttributes) lastChild;
            if (Objects.nonNull(imageAttributes) && !CollectionUtils.isEmpty(imageAttributes.getAttributes())) {
                String width = imageAttributes.getAttributes().get(KEY_WIDTH);
                String height = imageAttributes.getAttributes().get(KEY_HEIGHT);
                sb.append(StringUtils.isBlank(width) ? "" : (" " + KEY_WIDTH + "=\"" + width + "\""));
                sb.append(StringUtils.isBlank(height) ? "" : (" " + KEY_HEIGHT + "=\"" + height + "\""));
            }
        }
        sb.append(">");

        if (StringUtils.isNotBlank(imageTitle)) {
            // 图片下方文字
            sb.append(String.format("<span class=\"image-caption\">%s</span>", imageTitle));
        }
        // 设置html内容
        htmlWriter.raw(sb.toString());
        htmlWriter.line();
    }
}
