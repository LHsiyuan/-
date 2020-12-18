package com._520siyuan;

import com._520siyuan.lucene.LuceneApplication;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: 思源
 * \* Date: 2020-12-17
 * \* Time: 10:53
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@SpringBootTest(classes = LuceneApplication.class)
@RunWith(SpringRunner.class)
public class LuceneApplicationTest {

    @Test
    public void test(){

    }


    IndexWriter getIndexWriter()throws Exception{
        //指定索引库存放的路径
        //F:\temp\index
        Directory directory = FSDirectory.open(new File("F:\\temp\\index").toPath());
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();
        //todo 使用自定义分词器
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        //创建indexwriterCofig对象
//        IndexWriterConfig config = new IndexWriterConfig();
        //创建indexwriter对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        return indexWriter;
    }

    //创建索引
    @Test
    public void createIndex() throws Exception {

        IndexWriter indexWriter = getIndexWriter();
        //原始文档的路径
        File dir = new File("F:\\temp\\searchsource");
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File f : files) {
            //文件名
            String fileName = f.getName();
            //文件内容
            String fileContent = FileUtils.readFileToString(f);
            //文件路径
            String filePath = f.getPath();
            //文件的大小
            long fileSize  = FileUtils.sizeOf(f);
            //创建文件名域
            //第一个参数：域的名称
            //第二个参数：域的内容
            //第三个参数：是否存储
            Field fileNameField = new TextField("filename", fileName, Field.Store.YES);
            //文件内容域
            Field fileContentField = new TextField("content", fileContent, Field.Store.YES);
            //文件路径域（不分析、不索引、只存储）
            Field filePathField = new TextField("path", filePath, Field.Store.YES);
            //文件大小域
            Field fileSizeField = new TextField("size", fileSize + "", Field.Store.YES);

            //创建document对象
            Document document = new Document();
            document.add(fileNameField);
            document.add(fileContentField);
            document.add(filePathField);
            document.add(fileSizeField);
            //创建索引，并写入索引库
            indexWriter.addDocument(document);
        }
        //关闭indexwriter
        indexWriter.close();
    }


    @Test
    public void testTokenStream() throws Exception {
        //创建一个标准分析器对象
        Analyzer analyzer = new StandardAnalyzer();
        //获得tokenStream对象
        //第一个参数：域名，可以随便给一个
        //第二个参数：要分析的文本内容
        TokenStream tokenStream = analyzer.tokenStream("test", "The Spring Framework provides a comprehensive programming and configuration model.");
        //添加一个引用，可以获得每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //将指针调整到列表的头部
        tokenStream.reset();
        //遍历关键词列表，通过incrementToken方法判断列表是否结束
        while(tokenStream.incrementToken()) {
            //关键词的起始位置
            System.out.println("start->" + offsetAttribute.startOffset());
            //取关键词
            System.out.println(charTermAttribute);
            //结束位置
            System.out.println("end->" + offsetAttribute.endOffset());
        }
        tokenStream.close();
    }

    //添加索引
    @Test
    public void addDocument() throws Exception {
        //索引库存放路径
        Directory directory = FSDirectory.open(new File("F:\\temp\\index").toPath());
        File file = new File("F:\\temp\\1.txt");
        //文件内容
        String fileContent = FileUtils.readFileToString(file);
        //文件路径
        String filePath = file.getPath();
        //文件的大小
        long fileSize  = FileUtils.sizeOf(file);
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        //创建一个indexwriter对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域。
        //不同的document可以有不同的域，同一个document可以有相同的域。
        document.add(new TextField("filename", "新添加的文档", Field.Store.YES));
        document.add(new TextField("content", fileContent, Field.Store.NO));
        //LongPoint创建索引
        document.add(new LongPoint("size", fileSize));
        //StoreField存储数据
        document.add(new StoredField("size", fileSize));
        //不需要创建索引的就使用StoreField存储
        document.add(new StoredField("path",filePath));
        //添加文档到索引库
        indexWriter.addDocument(document);
        //关闭indexwriter
        indexWriter.close();

    }


    //删除全部索引
    @Test
    public void deleteAllIndex() throws Exception {
        IndexWriter indexWriter = getIndexWriter();
        //删除全部索引
        indexWriter.deleteAll();
        //关闭indexwriter
        indexWriter.close();
    }

    //根据查询条件删除索引
    @Test
    public void deleteIndexByQuery() throws Exception {
        IndexWriter indexWriter = getIndexWriter();
        //创建一个查询条件
        Query query = new TermQuery(new Term("filename", "apache"));
        //根据查询条件删除
        indexWriter.deleteDocuments(query);
        //关闭indexwriter
        indexWriter.close();
    }


    //修改索引库 原理就是先删除后添加
    @Test
    public void updateIndex() throws Exception {
        IndexWriter indexWriter = getIndexWriter();
        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域。
        //不同的document可以有不同的域，同一个document可以有相同的域。
        document.add(new TextField("filename", "要更新的文档", Field.Store.YES));
        document.add(new TextField("content", " Lucene 简介 Lucene 是一个基于 Java 的全文信息检索工具包," +
                "它不是一个完整的搜索应用程序,而是为你的应用程序提供索引和搜索功能。",
                Field.Store.YES));
        indexWriter.updateDocument(new Term("content", "java"), document);
        //关闭indexWriter
        indexWriter.close();
    }


    //获取 IndexSearcher
    IndexSearcher getIndexSearcher() throws Exception {
        //指定索引库存放的路径
        //F:\temp\index
        Directory directory = FSDirectory.open(new File("F:\\temp\\index").toPath());
        //创建indexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建indexsearcher对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        return indexSearcher;
    }

    //执行查询
    private void printResult(Query query, IndexSearcher indexSearcher) throws Exception {
        //执行查询,第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);
        //共查询到的document个数
        System.out.println("查询结果总数量：" + topDocs.totalHits);
        //遍历查询结果,topDocs.scoreDocs存储了document对象的id
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("filename"));
            //System.out.println(document.get("content"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println("-------------------------");
        }
        //关闭indexreader
        indexSearcher.getIndexReader().close();
    }

    //使用Termquery查询
    @Test
    public void testTermQuery() throws Exception {
        IndexSearcher indexSearcher = getIndexSearcher();
        //创建查询对象
        Query query = new TermQuery(new Term("filename", "java"));
        printResult(query, indexSearcher);
    }


    @Test
    public void testRangeQuery() throws Exception {
        IndexSearcher indexSearcher = getIndexSearcher();
        Query query = LongPoint.newRangeQuery("size", 0l, 10000l);
        printResult(query, indexSearcher);
    }


    @Test
    public void testQueryParser() throws Exception {
        IndexSearcher indexSearcher = getIndexSearcher();
        //创建queryparser对象
        //第一个参数默认搜索的域
        //第二个参数就是分析器对象
        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
        Query query = queryParser.parse("Lucene是java开发的");
        //执行查询
        printResult(query, indexSearcher);
    }

}
