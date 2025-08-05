package com.zhixing.weblog.web.markdown.provider;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;


public class NofollowLinkAttributeProvider implements AttributeProvider {
    // 网站域名
    private final static String DOMAIN = "www.windblog.website";

    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if (node instanceof Link) {
            Link linkNode = (Link) node;
            // 获取链接地址
            String href = linkNode.getDestination();
            // 如果链接不是自己域名，添加 ref="nofollow"
            if (!href.contains(DOMAIN)) {
                attributes.put("rel", "nofollow");
            }
        }
    }
}
