package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;
    private Random rand = new Random();

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        HashMap<String, TrieNode> temp = children;
        for (int i = 0; i < s.length(); i++){
            if (!temp.containsKey(String.valueOf(s.charAt(i)))){
                temp.put(String.valueOf(s.charAt(i)), new TrieNode());
            }
            if (i == s.length() - 1){
                temp.get(String.valueOf(s.charAt(i))).isWord = true;
            }
            temp = temp.get(String.valueOf(s.charAt(i))).children;
        }
    }

    private TrieNode searchNode(String s){
        TrieNode temp = this;
        for (int i = 0; i < s.length(); i++){
            if (temp.children.containsKey(String.valueOf(s.charAt(i)))){
                temp = temp.children.get(String.valueOf(s.charAt(i)));
            }else{
                return null;
            }
        }
        return temp;
    }

    public boolean isWord(String s) {
        TrieNode temp = searchNode(s);
        if (temp == null){
            return false;
        } else{
            return temp.isWord;
        }

    }

    public String getAnyWordStartingWith(String s) {
        TrieNode temp = searchNode(s);
        if (temp == null){
            return "noWord";
        }
        while (!temp.isWord){
            for (String c: temp.children.keySet()){
                temp = temp.children.get(c);
                s += c;
                break;
            }
        }
        return s;

    }

    public String getGoodWordStartingWith(String s) {
        String x = s;
        TrieNode temp = searchNode(s);
        if (temp == null){
            return "noWord";
        }
        // get a random word
        ArrayList<String> charsNoWord = new ArrayList<>();
        ArrayList<String> charsWord = new ArrayList<>();
        String c;

        while (true){
            charsNoWord.clear();
            charsWord.clear();
            for (String ch: temp.children.keySet()){
                if (temp.children.get(ch).isWord){
                    charsWord.add(ch);
                } else {
                    charsNoWord.add(ch);
                }
            }
            System.out.println("------>"+charsNoWord+" "+charsWord);
            if (charsNoWord.size() == 0){
                if(charsWord.size() == 0){
                    return "sameAsPrefix";
                }
                s += charsWord.get( rand.nextInt(charsWord.size()) );
                break;
            } else {
                c = charsNoWord.get( rand.nextInt(charsNoWord.size()) );
                s += c;
                temp = temp.children.get(c);
            }
        }
        if(x.equals(s)){
            return "sameAsPrefix";
        }else{
            return s;
        }

    }
}
