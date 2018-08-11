package org.elasticsearch;

import static org.junit.Assert.assertTrue;

import JVnSegmenter.*;
import org.apache.lucene.analysis.vi.VietnameseTokenizer;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
      public static String INPUT_FILE=	"samples/input.txt";
       public static String MODEL_DIR= "/models";
       public static String SAMPLES_DIR= "samples";
    private Iterator<TaggedWord> taggedWords;
    @Test
    public void testmain()
    {
//        -modeldir $(MODEL_DIR) -inputfile $(SAMPLES_DIR)/input.txt
//         -modeldir $(MODEL_DIR) -inputdir $(SAMPLES_DIR)/inputdir
        JVnSegmenter.main(new String[]{"-modeldir", MODEL_DIR,"-inputfile",INPUT_FILE });
        assertTrue( true );
    }

    String getWordMarkedData(Map lbInt2Str, List data){
        List<TaggedWord> words = new ArrayList<TaggedWord>();
        if (data == null)
            return null;
        String newword= "";
        String result = "";
        for (int i = 0; i < data.size(); ++i){
            List seq = (List) data.get(i);
            for (int j = 0;  j < seq.size(); j++){
                Observation obsr = (Observation) seq.get(j);

                //get the next observation in this sequence if available
                Observation nxtObsr = null;
                if (j < seq.size() -1 ) nxtObsr= (Observation) seq.get(j + 1);


                //get the label of current obsr
                String curLabelStr = (String)lbInt2Str.get(new Integer(obsr.modelLabel));

                //get the label of next obsr if the next observation is available
                String nxtLabelStr = null;
                if (nxtObsr != null){
                    nxtLabelStr = (String)lbInt2Str.get(new Integer(nxtObsr.modelLabel));
                }

                //if the label of the current observation is b-w
                //insert one [ before it
                if (curLabelStr.equalsIgnoreCase("b-w")) {
                    result += "[";
                    if (newword==null) newword="";
                }


                result += obsr.originalData;
                if(newword != null) newword += obsr.originalData;


                //if the label of the current observation is i-w
                //and the label of the next one is b-w or o
                //or the current observation is the last one in this sequence
                //insert ] after it
                if (curLabelStr.equalsIgnoreCase("i-w") || curLabelStr.equalsIgnoreCase("b-w")){
                    if (nxtLabelStr == null)
                    {
                        result += "]";
                        if(newword !=null) {
//                            System.out.println("===word==" + newword);
                            words.add(new TaggedWord("word",newword));
                            newword = null;
                        }
                    }
                    else if (nxtLabelStr.equalsIgnoreCase("b-w") || nxtLabelStr.equalsIgnoreCase("o")){
                        {
                            result += "]";
                            if(newword !=null) {
//                                System.out.println("===word==" + newword);
                                words.add(new TaggedWord("word",newword));
                                newword = null;
                            }
                        }
                    }
                    else {
                        newword +=" ";
                    }
                }
                result += " ";
            } //end of current sequence
            result += "\n";
        }
        System.out.println("==============");

        taggedWords = words.iterator();
        while (taggedWords.hasNext()) {
            final TaggedWord word = taggedWords.next();
            System.out.print("["+word + "] ");
        }
        System.out.println("\n==============");
        return result.trim();
    }

    @Test
    public void mytest()
    {
        TaggingInputData taggerData = new TaggingInputData();
        if (!taggerData.init(MODEL_DIR)) assertTrue( false );
//        taggerData.readOriginalDataFromFile(INPUT_FILE);
        taggerData.readOriginalDataFromString("công nghệ Việt Nam");

        Maps taggerMaps = new Maps();
        Dictionary taggerDict = new Dictionary();
        FeatureGen taggerFGen = new FeatureGen(taggerMaps, taggerDict);
        Viterbi taggerVtb = new Viterbi();
        Option taggerOpt = new Option(MODEL_DIR);
        if (!taggerOpt.readOptions()) {
            assertTrue( false );
        }
        Model taggerModel = new Model(taggerOpt, taggerMaps, taggerDict, taggerFGen, taggerVtb);
        if (!taggerModel.init()) {
            System.out.println("Couldn't load the model");
            System.out.println("Check the <model directory> and the <model file> again");
            assertTrue( false );
        }

        taggerData.cpGen(taggerMaps.cpStr2Int);

        //inference
        taggerModel.inferenceAll(taggerData.data);
        System.out.print(getWordMarkedData(taggerMaps.lbInt2Str,taggerData.data));
//        taggerData.writeWordMarkedData(taggerMaps.lbInt2Str, INPUT_FILE+ ".wseg");
        assertTrue( true );
    }

    @Test
    public  void testPlugIn() throws IOException {
        VietnameseTokenizer vietTokenizer = new VietnameseTokenizer();
        vietTokenizer.tokenize(new StringReader("công nghệ Việt Nam"));

    }
}
