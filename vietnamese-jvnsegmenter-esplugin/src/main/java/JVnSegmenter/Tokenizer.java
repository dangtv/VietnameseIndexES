package JVnSegmenter;


import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.*;

/**
 * @author LE Hong Phuong, phuonglh@gmail.com
 * <p>
 * The Vietnamese tokenizer.
 */

public class Tokenizer  {
    public static String MODEL_DIR= "/models";
	/**
	 * The current input stream
	 */
	private InputStream inputStream;

	/**
	 * Current reader, keep track of our position within the input file
	 */
	private LineNumberReader lineReader;

	/**
	 * Current line
	 */
	private String line;

	/**
	 * Current column
	 */
	private int column;

	/**
	 * A list of tokens containing the result of tokenization
	 */
	private List<TaggedWord> result = null;
    private TaggingInputData taggerData = null;
    private Maps taggerMaps=null;
    private Model taggerModel = null;
	/**
	 * Creates a tokenizer
	 */
	public Tokenizer() {
	    taggerData = new TaggingInputData();
        if (!taggerData.init(MODEL_DIR)) {
            System.out.println("Couldn't load the model");
            System.out.println("Check the <model directory> and the <model file> again");
            return;
        }
        taggerMaps = new Maps();
        Dictionary taggerDict = new Dictionary();
        FeatureGen taggerFGen = new FeatureGen(taggerMaps, taggerDict);
        Viterbi taggerVtb = new Viterbi();
        Option taggerOpt = new Option(MODEL_DIR);
        if (!taggerOpt.readOptions()) {
            return;
        }
        taggerModel = new Model(taggerOpt, taggerMaps, taggerDict, taggerFGen, taggerVtb);
        if (!taggerModel.init()) {
            System.out.println("Couldn't load the model");
            System.out.println("Check the <model directory> and the <model file> again");
            return;
        }
	}


    private void getWordMarkedData(Map lbInt2Str, List data){
        List<TaggedWord> words = new ArrayList<TaggedWord>();
        if (data == null)
            return;
        StringBuilder newword= new StringBuilder();
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
//                    result += "[";
                    if (newword==null) newword = new StringBuilder();
                }


//                result += obsr.originalData;
                if(newword != null) newword.append(obsr.originalData);


                //if the label of the current observation is i-w
                //and the label of the next one is b-w or o
                //or the current observation is the last one in this sequence
                //insert ] after it
                if (curLabelStr.equalsIgnoreCase("i-w") || curLabelStr.equalsIgnoreCase("b-w")){
                    if (nxtLabelStr == null)
                    {
//                        result += "]";
                        if(newword !=null) {
//                            System.out.println("===word==" + newword);
                            words.add(new TaggedWord("word", newword.toString()));
                            newword = null;
                        }
                    }
                    else if (nxtLabelStr.equalsIgnoreCase("b-w") || nxtLabelStr.equalsIgnoreCase("o")){
                        {
//                            result += "]";
                            if(newword !=null) {
//                                System.out.println("===word==" + newword);
                                words.add(new TaggedWord("word", newword.toString()));
                                newword = null;
                            }
                        }
                    }
                    else {
                        newword.append(" ");
                    }
                }
//                result += " ";
            } //end of current sequence
//            result += "\n";
        }
//        System.out.println("==============");

        result = words;
//        while (taggedWords.hasNext()) {
//            final TaggedWord word = taggedWords.next();
//            System.out.print("["+word + "] ");
//        }
//        System.out.println("\n==============");
    }

	/**
	 * Tokenize a reader. If ambiguities are not resolved, this method
	 * selects the first segmentation for a phrase if there are more than one
	 * segmentations. Otherwise, it selects automatically the most
	 * probable segmentation returned by the ambiguity resolver.
	 */
    public void tokenize(Reader input) throws IOException {
//        taggerData.readOriginalDataFromFile(INPUT_FILE);
        LineNumberReader lineReader = new LineNumberReader(input);
        String line = "";
        StringBuilder allText = new StringBuilder();
        while ((line = lineReader.readLine()) != null) {
            allText.append("\n ");
            allText.append(line.trim());
        }
        taggerData.readOriginalDataFromString(allText.toString());
        taggerData.cpGen(taggerMaps.cpStr2Int);
        //inference
        taggerModel.inferenceAll(taggerData.data);
        getWordMarkedData(taggerMaps.lbInt2Str,taggerData.data);
//        taggerData.writeWordMarkedData(taggerMaps.lbInt2Str, INPUT_FILE+ ".wseg");

    }

	/**
	 * @return Returns the a result of tokenization.
	 */
	public List<TaggedWord> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            The result to set.
	 */
	public void setResult(List<TaggedWord> result) {
		this.result = result;
	}

}
