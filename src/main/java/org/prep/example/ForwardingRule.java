package org.prep.example;

import java.util.*;


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

class ForwardingRule {
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
        List<ConnectedSentences> listOfNodes = createTree(strings);
        for(ConnectedSentences entry: listOfNodes) {
            printEntry(entry.head, entry.children, 0);
        }
    }

    public static List<ConnectedSentences> createTree(List<String> input) {
        List<ConnectedSentences> listOfNodes = new ArrayList<>();
        for (String single : input) {
            StringBuilder builder = new StringBuilder();
            List<String> words = new ArrayList();
            for (char each : single.toCharArray()) {
                if (each == '/') {
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
                ConnectedSentences forEntry = createConnectedSentences(words.get(0), words, 1);
                ConnectedSentences existing = null;
                for (ConnectedSentences cs : listOfNodes) {
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

    static ConnectedSentences createConnectedSentences(String start, List<String> words, int arrayStartIndex) {
        ConnectedSentences first = new ConnectedSentences();
        first.head = start;
        ConnectedSentences node = first;
        int counter = 0;
        for (String word : words) {
            counter++;
            if (counter <= arrayStartIndex) {
                continue;
            }
            ConnectedSentences inner = new ConnectedSentences();
            inner.head = word;
            inner.children = new LinkedHashSet<>();
            node.children = new LinkedHashSet<>();
            node.children.add(inner);
            node = inner;
        }
        return first;
    }

    static void printEntry(String head, Set<ConnectedSentences> sentences, int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth * 2; i++)
            builder.append(" ");
        builder.append("-").append(" ");
        System.out.println(builder.toString() + head);
        if (sentences == null || sentences.size() == 0) return;
        for (ConnectedSentences sentence : sentences) {
            printEntry(sentence.head, sentence.children, depth + 1);
        }
    }
}

class ConnectedSentences {
    String head;
    Set<ConnectedSentences> children = null;

    void append(Set<ConnectedSentences> source) {
        if (source == null || source.size() == 0) return;
        for (ConnectedSentences child : source) {
            for (ConnectedSentences target : children) {
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