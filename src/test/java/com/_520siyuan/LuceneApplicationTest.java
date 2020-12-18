package com._520siyuan;

import com._520siyuan.lucene.LuceneApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


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

//
//    //创建索引
//    @Test
//    public void createIndex() throws Exception {
//
//        //指定索引库存放的路径
//        //F:\temp\index
//        Directory directory = FSDirectory.open(new File("F:\\temp\\index").toPath());
//        //索引库还可以存放到内存中
//        //Directory directory = new RAMDirectory();
//        //使用自定义分词器
////        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
//        //创建indexwriterCofig对象
//        IndexWriterConfig config = new IndexWriterConfig();
//        //创建indexwriter对象
//        IndexWriter indexWriter = new IndexWriter(directory, config);
//        //原始文档的路径
//        File dir = new File("F:\\temp\\searchsource");
//        File[] files = dir.listFiles();
//        if (files == null) return;
//        for (File f : files) {
//            //文件名
//            String fileName = f.getName();
//            //文件内容
//            String fileContent = FileUtils.readFileToString(f);
//            //文件路径
//            String filePath = f.getPath();
//            //文件的大小
//            long fileSize  = FileUtils.sizeOf(f);
//            //创建文件名域
//            //第一个参数：域的名称
//            //第二个参数：域的内容
//            //第三个参数：是否存储
//            Field fileNameField = new TextField("filename", fileName, Field.Store.YES);
//            //文件内容域
//            Field fileContentField = new TextField("content", fileContent, Field.Store.YES);
//            //文件路径域（不分析、不索引、只存储）
//            Field filePathField = new TextField("path", filePath, Field.Store.YES);
//            //文件大小域
//            Field fileSizeField = new TextField("size", fileSize + "", Field.Store.YES);
//
//            //创建document对象
//            Document document = new Document();
//            document.add(fileNameField);
//            document.add(fileContentField);
//            document.add(filePathField);
//            document.add(fileSizeField);
//            indexWriter.deleteAll();
//            //创建索引，并写入索引库
//            indexWriter.addDocument(document);
//        }
//        //关闭indexwriter
//        indexWriter.close();
//    }
//
//
//
//    //查询索引库
//    @Test
//    public void searchIndex() throws Exception {
//        //指定索引库存放的路径
//        //F:\temp\index
//        Directory directory = FSDirectory.open(new File("F:\\temp\\index").toPath());
//        //创建indexReader对象
//        IndexReader indexReader = DirectoryReader.open(directory);
//        //创建indexsearcher对象
//        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        //创建查询
//        Query query = new TermQuery(new Term("filename", "docx"));
//        //执行查询
//        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
//        TopDocs topDocs = indexSearcher.search(query, 10);
//        //查询结果的总条数
//        System.out.println("查询结果的总条数："+ topDocs.totalHits);
//        //遍历查询结果
//        //topDocs.scoreDocs存储了document对象的id
//        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
//            //scoreDoc.doc属性就是document对象的id
//            //根据document的id找到document对象
//            Document document = indexSearcher.doc(scoreDoc.doc);
//            System.out.println(document.get("filename"));
//            //System.out.println(document.get("content"));
//            System.out.println(document.get("path"));
//            System.out.println(document.get("size"));
//            System.out.println("-------------------------");
//        }
//        //关闭indexreader对象
//        indexReader.close();
//    }
//
//    @Test
//    public void testTokenStream() throws Exception {
//        //创建一个标准分析器对象
//        Analyzer analyzer = new StandardAnalyzer();
//        //获得tokenStream对象
//        //第一个参数：域名，可以随便给一个
//        //第二个参数：要分析的文本内容
//        TokenStream tokenStream = analyzer.tokenStream("test", "The Spring Framework provides a comprehensive programming and configuration model.");
//        //添加一个引用，可以获得每个关键词
//        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
//        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
//        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
//        //将指针调整到列表的头部
//        tokenStream.reset();
//        //遍历关键词列表，通过incrementToken方法判断列表是否结束
//        while(tokenStream.incrementToken()) {
//            //关键词的起始位置
//            System.out.println("start->" + offsetAttribute.startOffset());
//            //取关键词
//            System.out.println(charTermAttribute);
//            //结束位置
//            System.out.println("end->" + offsetAttribute.endOffset());
//        }
//        tokenStream.close();
//    }

}
