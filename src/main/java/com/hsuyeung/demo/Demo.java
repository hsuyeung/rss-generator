package com.hsuyeung.demo;

import com.hsuyeung.rss.Item;
import com.hsuyeung.rss.RSS;
import com.hsuyeung.rss.Writer;
import com.hsuyeung.util.DateUtil;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 使用 demo
 * @author hsuyeung
 * @date 2023/03/18
 */
public class Demo {
    @SneakyThrows
    public static void main(String[] args) {
        // 模拟生成 10 篇文章的 item
        List<Item> itemList = IntStream.range(0, 10)
                .mapToObj(i -> Item.builder()
                        .title("文章标题" + i)
                        .link("文章的访问链接" + i)
                        // 或者你直接把你全部内容放里面也行，这样阅读器就可以直接看到全文内容，
                        // 但是记得内容用 CDATA 标签包裹起来避免出现问题
                        // 例如：<![CDATA[文本内容]]>
                        .description("文章的描述信息" + i)
                        .comments("文章的评论链接" + i)
                        .guid("文章的唯一 id，最好是使用文章的访问链接" + i)
                        .pubDate(DateUtil.formatLocalDateTimeToRFC822String(LocalDateTime.now(), Locale.ENGLISH))
                        .source(Item.Source.builder()
                                .url("文章来源的网站链接" + i)
                                .value("文章来源的网站名称" + i)
                                .build())
                        .build())
                .collect(Collectors.toList());
        LocalDateTime now = LocalDateTime.now();
        String rfc822 = DateUtil.formatLocalDateTimeToRFC822String(now, Locale.ENGLISH);
        RSS rss = RSS.builder()
                .title("网站的标题")
                .link("网站的链接")
                .description("网站的描述信息")
                // 网站的语言
                .language("zh")
                .copyright("版权信息")
                // RSS 文件的发布时间
                .pubDate(rfc822)
                // RSS 文件的最后一个构建时间
                .lastBuildDate(rfc822)
                .category("分类")
                .generator("该 RSS 文件的生成者")
                // 文档链接
                .docs("https://www.rssboard.org/rss-specification")
                // item 列表，可以为空，为空就说明没有文章
                .itemList(itemList)
                .build();
        new Writer(rss, "./rss-gen-demo.xml").write();
    }
}
