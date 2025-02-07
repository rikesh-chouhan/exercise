package org.prep.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordPermutationOfWords {

    public static final String[] ARR_1 = {"dhvf","sind","ffsl","yekr","zwzq","kpeo","cila","tfty","modg","ztjg","ybty","heqg","cpwo","gdcj","lnle","sefg","vimw","bxcb"};
    public static final String[] ARR_2 = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"};
    public static final String[] ARR_3 = {"ab", "cd", "ef"};
    public static final String TO_SEARCH = "pjzkrkevzztxductzzxmxsvwjkxpvukmfjywwetvfnujhweiybwvvsr" +
            "fequzkhossmootkmyxgjgfordrpapjuunmqnxxdrqrfgkrsjqbszgiqlcfnrpjlcwdrvbumtotzylshdvccdm" +
            "sqoadfrpsvnwpizlwszrtyclhgilklydbmfhuywotjmktnwrfvizvnmfvvqfiokkdprznnnjycttprkxpuykh" +
            "mpchiksyucbmtabiqkisgbhxngmhezrrqvayfsxauampdpxtafniiwfvdufhtwajrbkxtjzqjnfocdhekumtt" +
            "uqwovfjrgulhekcpjszyynadxhnttgmnxkduqmmyhzfnjhducesctufqbumxbamalqudeibljgbspeotkgvdd" +
            "cwgxidaiqcvgwykhbysjzlzfbupkqunuqtraxrlptivshhbihtsigtpipguhbhctcvubnhqipncyxfjebdnjy" +
            "etnlnvmuxhzsdahkrscewabejifmxombiamxvauuitoltyymsarqcuuoezcbqpdaprxmsrickwpgwpsoplhug" +
            "bikbkotzrtqkscekkgwjycfnvwfgdzogjzjvpcvixnsqsxacfwndzvrwrycwxrcismdhqapoojegggkocyrdt" +
            "kzmiekhxoppctytvphjynrhtcvxcobxbcjjivtfjiwmduhzjokkbctweqtigwfhzorjlkpuuliaipbtfldiny" +
            "etoybvugevwvhhhweejogrghllsouipabfafcxnhukcbtmxzshoyyufjhzadhrelweszbfgwpkzlwxkogyogu" +
            "tscvuhcllphshivnoteztpxsaoaacgxyaztuixhunrowzljqfqrahosheukhahhbiaxqzfmmwcjxountkevsv" +
            "pbzjnilwpoermxrtlfroqoclexxisrdhvfsindffslyekrzwzqkpeocilatftymodgztjgybtyheqgcpwogdc" +
            "jlnlesefgvimwbxcbzvaibspdjnrpqtyeilkcspknyylbwndvkffmzuriilxagyerjptbgeqgebiaqnvdubrt" +
            "xibhvakcyotkfonmseszhczapxdlauexehhaireihxsplgdgmxfvaevrbadbwjbdrkfbbjjkgcztkcbwagtcn" +
            "rtqryuqixtzhaakjlurnumzyovawrcjiwabuwretmdamfkxrgqgcdgbrdbnugzecbgyxxdqmisaqcyjkqrntx" +
            "qmdrczxbebemcblftxplafnyoxqimkhcykwamvdsxjezkpgdpvopddptdfbprjustquhlazkjfluxrzopqdst" +
            "ulybnqvyknrchbphcarknnhhovweaqawdyxsqsqahkepluypwrzjegqtdoxfgzdkydeoxvrfhxusrujnmjzqr" +
            "rlxglcmkiykldbiasnhrjbjekystzilrwkzhontwmehrfsrzfaqrbbxncphbzuuxeteshyrveamjsfiaharkc" +
            "qxefghgceeixkdgkuboupxnwhnfigpkwnqdvzlydpidcljmflbccarbiegsmweklwngvygbqpescpeichmfid" +
            "gsjmkvkofvkuehsmkkbocgejoiqcnafvuokelwuqsgkyoekaroptuvekfvmtxtqshcwsztkrzwrpabqrrhnle" +
            "rxjojemcxel";


    public static void main(String[] args) {

        if (args[0].equals("3")) {
            new WordPermutationOfWords().combos(ARR_3, "hello")
                    .stream()
                    .forEach(System.out::println);
            return;
        }

        if (args[0].equals("1")) {
            long containsCount = Arrays.stream(ARR_1)
                    .filter(word -> TO_SEARCH.contains(word))
                    .count();
            System.out.println("count " + containsCount);
        }

        if (args[0].equals("2")) {
            new WordPermutationOfWords().printPermutationsIterative("1234");
        }
    }

    protected List<String> recurseWithRollover(List<String> entries) {
        List<String> computed = recurse(entries.toArray(new String[0]), entries.size());

        return computed;
    }

    protected List<String> recurse(String[] words, int index) {
        if (index <= 1) {
            List<String> result = new ArrayList<>();
            String word = String.join("", words);
            result.add(word);
            return result;
        } else {
            List<String> result = new ArrayList<>();
            for (String one : recurse(words, index - 1)) {
                result.add(one);
            }
            for (int i = 0; i < index - 1; i++) {
                if (index % 2 == 0) {
                    String temp = words[i];
                    words[i] = words[index - 1];
                    words[index - 1] = temp;
                } else {
                    String temp = words[0];
                    words[0] = words[index - 1];
                    words[index - 1] = temp;
                }
                for (String one : recurse(words, index - 1)) {
                    result.add(one);
                }
            }
            return result;
        }
    }

    protected Set<Integer> combos(String[] words, String toSearch) {
            Set<Integer> result = new HashSet<>();
            if (words.length <= 1) {
                String theWord = String.join("", words);
                result.addAll(foundIndexes(toSearch, theWord));
            } else {
                for (int i = 0; i < words.length; i++) {
                    int counter = i + 1;
                    for (int j = counter; j < counter + words.length; j++) {
                        int k = j;
                        if (k >= words.length) {
                            k = k % words.length;
                        }
                        if (i == k) continue;
                        System.out.println("["+i+","+k+"]");
                    }
                }
            }

            return result;
    }

    protected Set<Integer> foundIndexes(String searchIn, String toSearch) {
        int startFrom = 0;
        Set<Integer> indexes = new HashSet<>();
        while (true) {
            int index = searchIn.indexOf(toSearch, startFrom);
            if (index < 0) {
                break;
            } else {
                indexes.add(index);
                startFrom = index;
            }
        }

        return indexes;
    }

    private void printPermutationsIterative(String string){
        int [] factorials = new int[string.length()+1];
        factorials[0] = 1;
        for (int i = 1; i<=string.length();i++) {
            factorials[i] = factorials[i-1] * i;
        }

        for (int i = 0; i < factorials[string.length()]; i++) {
            String onePermutation="";
            String temp = string;
            int positionCode = i;
            for (int position = string.length(); position > 0 ;position--){
                int factorialAt = factorials[position-1];
                int selected = positionCode / factorialAt;
                onePermutation += temp.charAt(selected);
                System.out.print("{"+position+","+positionCode+","+factorialAt+","+selected+",");
                positionCode = positionCode % factorialAt;
                System.out.print(positionCode+"}");
                temp = temp.substring(0,selected) + temp.substring(selected+1);
            }
            System.out.println();
            //System.out.println(onePermutation);
        }
    }
}