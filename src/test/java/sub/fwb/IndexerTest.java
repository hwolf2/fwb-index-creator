package sub.fwb;

import static org.custommonkey.xmlunit.XMLAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IndexerTest {
	
	private OutputStream outputBaos;
	private static Xslt xslt;
	
	@BeforeClass
	public static void beforeAllTests() throws Exception {
		xslt = new Xslt("src/main/resources/fwb-indexer.xslt");
	}
	
	@Before
	public void beforeEachTest() throws Exception {
		outputBaos = new ByteArrayOutputStream();
	}

	@After
	public void afterEachTest() {
        //System.out.println(outputBaos.toString());	
	}
	
	@Test
	public void shouldTransformPrintedSource() throws Exception {
		xslt.transform("src/test/resources/printedSource.xml", outputBaos);
        String result = outputBaos.toString();

        assertXpathEvaluatesTo("artikel", "//field[@name='type']", result);
        assertXpathEvaluatesTo("Some printed source", "//field[@name='printedSource']", result);
        assertXpathEvaluatesTo("08", "//field[@name='volume']", result);
        assertXpathEvaluatesTo("1", "//field[@name='col']", result);
	}
	
	@Test
	public void shouldFindArticleEntries() throws Exception {
		xslt.transform("src/test/resources/articleEntries.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("some.id", "//field[@name='internal_id']", result);
        assertXpathEvaluatesTo("test_lemma,", "//field[@name='lemma']", result);
        assertXpathEvaluatesTo("test_lemma", "//field[@name='lemma_normalized']", result);
        assertXpathEvaluatesTo("Pron.", "//field[@name='type_of_word']", result);
	}
	
	@Test
	public void shouldTransformLemmaWithoutComma() throws Exception {
		xslt.transform("src/test/resources/lemmaWithoutComma.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("test_lemma", "//field[@name='lemma']", result);
        assertXpathEvaluatesTo("test_lemma", "//field[@name='lemma_normalized']", result);
	}
	
	@Test
	public void shouldTransformOneVariant() throws Exception {
		xslt.transform("src/test/resources/oneNotationVariant.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("1", "count(//field[@name='notation_variant'])", result);
        assertXpathEvaluatesTo("tesst", "//field[@name='notation_variant']", result);
	}
	
	@Test
	public void shouldTransformTwoVariants() throws Exception {
		xslt.transform("src/test/resources/twoNotationVariants.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("2", "count(//field[@name='notation_variant'])", result);
        assertXpathEvaluatesTo("tesst", "//field[@name='notation_variant'][1]", result);
        assertXpathEvaluatesTo("tesdt", "//field[@name='notation_variant'][2]", result);
	}
	
	@Test
	public void shouldRecognizeRefArticle() throws Exception {
		xslt.transform("src/test/resources/articleRef.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("true", "//field[@name='is_reference']", result);
	}
	
	@Test
	public void shouldRecognizeNormalArticle() throws Exception {
		xslt.transform("src/test/resources/articleNotRef.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("false", "//field[@name='is_reference']", result);
	}
	
	@Test
	public void shouldTransformOneSense() throws Exception {
		xslt.transform("src/test/resources/oneSense.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("1", "count(//field[text()='bedeutung'])", result);
        assertXpathExists("//field[text()='Definition.']", result);
	}
	
	@Test
	public void shouldTransformTwoSenses() throws Exception {
		xslt.transform("src/test/resources/twoSenses.xml", outputBaos);
        String result = outputBaos.toString();
		
        assertXpathEvaluatesTo("2", "count(//field[text()='bedeutung'])", result);
        assertXpathExists("//field[text()='Definition one.']", result);
        assertXpathExists("//field[text()='Definition two.']", result);
	}
	
	@Test
	public void should() throws Exception {
		//xslt.transform("/home/dennis/temp/i/i/in.in.s.7pr.xml", outputBaos);
 		
 	}
	@Test
	public void shouldRef() throws Exception {
		//xslt.transform("/home/dennis/temp/i/i/it.it.s.9ref.xml", outputBaos);
 		
 	}
	@Test
	public void shouldArzeneien() throws Exception {
		//xslt.transform("/home/dennis/Downloads/arzneien.arzneien.s.3v.xml", outputBaos);
 		
 	}

}