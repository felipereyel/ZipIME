/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zipime;

/**
 *
 * @author felip
 */
public class ZipIME {
    
    public static void encode(){
        System.out.print("Full path: ");
        String path = FileHandler.getPath();
        
        String originalContent = FileHandler.read(path, "");
        
        System.out.print("Integer Key: ");
        int key = FileHandler.getKey();
        
        String codedContent = Cipher.encode( originalContent, key );
        
        Huffman huff = new Huffman();
        int[] charFreqs = Huffman.buildCharFreqs(codedContent);
        HuffmanTree tree = Huffman.buildTree(charFreqs);
        huff.buildCodes(tree, new StringBuffer());
        
        String pseudoHuffCode = huff.buildPseudoHuff(codedContent);
        String realHuffCode = huff.buildRealHuff(pseudoHuffCode);
        
        FileHandler.write(path.replace(".txt", " - PseudoCoded.txt"), pseudoHuffCode);        
        FileHandler.write(path.replace(".txt", " - Coded.txt"), realHuffCode);        
        FileHandler.write(path.replace(".txt", " - Codes.txt"), huff.buildStringDictCodes());
    };
    
    public static void decode(){
        System.out.print("Full coded file path: ");
        String path = FileHandler.getPath();
        
        String huffedContent = FileHandler.read(path, "");
        String pseudoHuffCode = Huffman.retrievePseudoHuff(huffedContent);
        System.out.println(pseudoHuffCode);
        
        String stringDictCodes = FileHandler.read(path.replace("Coded", "Codes"), "");
        
        Huffman huff = new Huffman();
        String[] dictCodes = Huffman.retrieveDictCodes(stringDictCodes); 
        huff.dictCode = dictCodes;
        
        
               
        //System.out.print("Decode Key: ");
        //int key = FileHandler.getKey();
                       
        //HuffmanTree tree = Huffman.buildTree(charFreqs);
        //huff.buildCodes(tree, new StringBuffer());
        
        //String pseudoHuffCode = huff.buildPseudoHuff(codedContent);
        //String realHuffCode = huff.buildRealHuff(pseudoHuffCode);
        
        //FileHandler.write(path.replace(".txt", " - PseudoCoded.txt"), pseudoHuffCode);        
        //FileHandler.write(path.replace(".txt", " - Coded.txt"), realHuffCode);        
        //FileHandler.write(path.replace(".txt", " - Codes.txt"), huff.buildStringDictCodes());
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // String str = "The quick brown fox Jumped over the lazy Dog";
        // String coded = Cipher.encode( str, 12 );
        // String decoded = Cipher.decode( coded, 12 );
        // System.out.println(coded);
        // System.out.println(decoded);
        
        // String test = "this is an example for huffman encoding";
        // int[] charFreqs = Huffman.buildCharFreqs(test);
        // HuffmanTree tree = Huffman.buildTree(charFreqs);
        // Huffman.printCodes(tree, new StringBuffer());
        
        // String path = FileHandler.getPath();
        // System.out.println(FileHandler.read(path));
        // System.out.println(FileHandler.write(path, "OhhhhYeeees"));
        // System.out.println(FileHandler.read(path));
        // 110111110000110101010101111000001110
        // 110111110000110101010101111000001110
        
        decode();
        // String str = "geekss@@for@geekss"; 
        // String[] arrOfStr = str.split("@"); 
        // for (String a : arrOfStr) 
        //     System.out.println(a);
    }
}