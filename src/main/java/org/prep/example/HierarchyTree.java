package org.prep.example;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Input:
 * <p>
 * /home/anti-depressants/xanax
 * /drugs/topical
 * /home/heart/lipitor
 * /home/heart/atorvastatin
 * /drugs/routes/oral/tablets
 * /drugs/routes/nasal/flonase
 * /home/heart/lipitor
 * /home/blood/sweat/tears
 * /drugs/nasal/flonase
 * <p>
 * /home
 * /anti-depressants
 * /xanax
 */

class HierarchyTree {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("/home/anti-depressants/xanax");
        strings.add("/drugs/topical");
        strings.add("/home/heart/lipitor");
        strings.add("/home/heart/atorvastatin");
        strings.add("/drugs/routes/oral/tablets");
        strings.add("/drugs/routes/nasal/flonase");
        strings.add("/home/heart/lipitor/paxol/diluter");
        strings.add("/home/heart/lipitor/paxol/bloodline");
        strings.add("/home/blood/sweat/tears");
        strings.add("/drugs/nasal/flonase");

        // for (String string : strings) {
        //   System.out.println(string);
        // }
        // System.out.println("Output");
        List<WordSequence> listOfNodes = createTree(strings, '/');
        for(WordSequence entry: listOfNodes) {
            printEntry(entry.head, entry.children, 0);
        }
    }

    /**
     * Create the tree from given list of directory style strings
     *
     * @param input
     * @return
     */
    public static List<WordSequence> createTree(List<String> input, char delimiter) {
        List<WordSequence> listOfNodes = new ArrayList<>();
        for (String single : input) {
            StringBuilder builder = new StringBuilder();
            List<String> words = new ArrayList();
            for (char each : single.toCharArray()) {
                if (each == delimiter) {
                    if (builder.length() > 0) {
                        words.add(builder.toString());
                        builder.delete(0, builder.length());
                    }
                } else {
                    builder.append(each);
                }
            }
            if (builder.length() > 0) {
                words.add(builder.toString());
            }
            if (words.size() > 0) {
                WordSequence forEntry = createSequenceChain(words.get(0), words, 1);
                WordSequence existing = null;
                for (WordSequence cs : listOfNodes) {
                    if (cs.head.equalsIgnoreCase(forEntry.head)) {
                        existing = cs;
                        break;
                    }
                }
                if (existing != null) {
                    existing.append(forEntry.children);
                } else {
                    listOfNodes.add(forEntry);
                }
            }
        }
        return listOfNodes;
    }

    /**
     * Create a chain from given hierarchical string
     *
     * @param start
     * @param words
     * @param arrayStartIndex
     * @return
     */
    static WordSequence createSequenceChain(String start, List<String> words, int arrayStartIndex) {
        WordSequence first = new WordSequence();
        first.head = start;
        WordSequence node = first;
        int counter = 0;
        for (String word : words) {
            counter++;
            if (counter <= arrayStartIndex) {
                continue;
            }
            WordSequence inner = new WordSequence();
            inner.head = word;
            inner.children = new LinkedHashSet<>();
            node.children = new LinkedHashSet<>();
            node.children.add(inner);
            node = inner;
        }
        return first;
    }

    static void printEntry(String head, Set<WordSequence> sentences, int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth * 2; i++)
            builder.append(" ");
        builder.append("-").append(" ");
        System.out.println(builder.toString() + head);
        if (sentences == null || sentences.size() == 0) return;
        for (WordSequence sentence : sentences) {
            printEntry(sentence.head, sentence.children, depth + 1);
        }
    }
}

/**
 * A Node representing a word sequence.
 */
class WordSequence {
    String head;
    Set<WordSequence> children = null;

    void append(Set<WordSequence> source) {
        if (source == null || source.size() == 0) return;
        for (WordSequence child : source) {
            for (WordSequence target : children) {
                if (child.head.equalsIgnoreCase(target.head) && child.children != null) {
                    target.append(child.children);
                    source.remove(child);
                }
            }
        }
        if (source.size() > 0) {
            children.addAll(source);
        }
    }
}