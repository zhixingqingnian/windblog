package com.zhixing.weblog.web.markdown;

import com.zhixing.weblog.web.markdown.provider.NofollowLinkAttributeProvider;
import com.zhixing.weblog.web.markdown.renderer.ImageNodeRenderer;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;


public class MarkdownHelper {
    // markdown 解析器
    private final static Parser PARSER;
    // html 渲染器
    private final static HtmlRenderer HTML_RENDERER;

    /**
     * 初始化
     */
    static {
        // markdown 扩展
        List<Extension> extensions = Arrays.asList(
                // 表格
                TablesExtension.create(),
                // 标题锚定位
                HeadingAnchorExtension.create(),
                // 图片宽高
                ImageAttributesExtension.create(),
                // 任务列表
                TaskListItemsExtension.create()
        );

        PARSER = Parser.builder().extensions(extensions).build();
        HTML_RENDERER = HtmlRenderer.builder()
                .extensions(extensions)
                .attributeProviderFactory(context -> new NofollowLinkAttributeProvider())
                .nodeRendererFactory(context -> new ImageNodeRenderer(context))
                .build();
    }

    /**
     * 将 markdown 转换为 html
     *
     * @param markdown
     * @return
     */
    public static String convertMarkdownToHtml(String markdown) {
        Node document = PARSER.parse(markdown);
        return HTML_RENDERER.render(document);
    }

    public static void main(String[] args) {
        String markdown = "*markdown 文本测试*~~123~~";
        System.out.println(MarkdownHelper.convertMarkdownToHtml(markdown));
    }
}
