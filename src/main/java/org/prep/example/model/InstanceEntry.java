package org.prep.example.model;

import org.prep.example.Pair;

import java.util.ArrayList;
import java.util.List;

public class InstanceEntry {
    private int id;
    private List<Long> enter;
    private List<Long> exit;

    public InstanceEntry(int theId) {
        this(theId, new ArrayList(), new ArrayList());
    }

    public InstanceEntry(int thdId, List<Long> entries, List<Long> exits) {
        this.id = thdId;
        this.enter = entries;
        this.exit = exits;
    }

    public void addEntry(long entry) {
        this.enter.add(entry);
    }

    public void addExit(long anExit) {
        this.exit.add(anExit);
    }

    public List<Pair<Long,Long>> getEntryExitPairs() {
        int i = 0;
        List<Pair<Long, Long>> pairList = new ArrayList();
        while (i < enter.size() && i < exit.size()) {
            pairList.add(new Pair(enter.get(i), exit.get(i)));
            i++;
        }
        return pairList;
    }


    public int getId() {
        return id;
    }

    public List<Long> getEnter() {
        return enter;
    }

    public List<Long> getExit() {
        return exit;
    }

}
