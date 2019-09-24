/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zipime;
import java.util.*;
import java.lang.Math; 
 
abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }
 
    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents
 
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}
 
class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees
 
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
 
public class Huffman {
    
    public String[] dictCode = new String[256];
    
    public char subsToChar(String subs){
        int i = 0;
        char res = 0;
        for (char c: subs.toCharArray()){
            res += (Math.pow(2, i)*Character.getNumericValue(c));
        }
        return res;
    };
    
    public static String charToSubs(char c){
        String subs = "";
        int Byte = c;

        for (int i = 0; i < 8; i++){
            int Bit = Byte%2;
            Byte /= 2;
            subs += Integer.toString(Bit);
        }
        
        return subs;
    };
    
    public String buildRealHuff(String pseudoHuffCode){
        String realHuffCode = "";
        
        for(int i = 0; i< pseudoHuffCode.length()/8; i++){
            String subs = pseudoHuffCode.substring(8*i, 8*(i+1));
            realHuffCode += String.valueOf(subsToChar(subs));
        }
        
        String subs = pseudoHuffCode.substring(8*(pseudoHuffCode.length()/8));
        realHuffCode += String.valueOf(subsToChar(subs));
        
        return realHuffCode;
    };
    
    public static String retrievePseudoHuff(String huffedContent){
        String pseudoHuffCode = "";
        for (char c: huffedContent.toCharArray()){
            pseudoHuffCode += charToSubs(c);
        }
        return pseudoHuffCode;
    }
    
    public String buildPseudoHuff(String codedContent){
        String pseudoHuffCode = "";
        for (char c : codedContent.toCharArray())
            pseudoHuffCode += dictCode[c];
        return pseudoHuffCode;
    };
    
    public static int[] buildCharFreqs(String source){
        int[] charFreqs = new int[256];
        for (char c : source.toCharArray())
            charFreqs[c]++;
        return charFreqs;
    };
    
    public String buildStringDictCodes(){
        String strDictCode = "";
        for(String code: dictCode)
            if (code == null){
                strDictCode += "@";
            } else {
                strDictCode += code+"@";
            }
        return strDictCode;         
    };
    
    public static String[] retrieveDictCodes(String stringDictCodes){
        String[] corruptDictCode = stringDictCodes.split("@");
        return Arrays.copyOf(corruptDictCode, corruptDictCode.length-1);
    };
    
    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
 
        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
 
    public void buildCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            dictCode[leaf.value] = prefix.toString();
 
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // traverse left
            prefix.append('0');
            buildCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            buildCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
}
