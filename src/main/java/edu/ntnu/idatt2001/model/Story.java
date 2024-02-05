package edu.ntnu.idatt2001.model;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Story {
  private String title;
  private HashMap<Link, Passage> passages;
  private Passage openingPassage;

  public Story(String title, Passage openingPassage) {
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<Link, Passage>();
    addPassage(openingPassage);
  }

  public String getTitle() {
    return title;
  }

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  public void setOpeningPassage(Passage openingPassage) {
    this.openingPassage = openingPassage;
  }

  public void addPassage(Passage passage) {
    Link newLink = new Link(passage.getTitle(), passage.getTitle());
    passage.addLink(newLink);
    passages.put(newLink, passage);
  }

  public Passage getPassage(Link link) {
    return passages.entrySet().stream()
        .filter(entry -> entry.getValue().getTitle().equals(link.getReference()))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElse(null);
  }

  public Collection<Passage> getPassages() {
    return passages.values();
  }

  public void removePassage(Link link) {
    Passage passageToRemove = getPassage(link);
    boolean linksToPassage = passages.values().stream()
        .filter(passage -> !passage.equals(passageToRemove))
        .flatMap(passage -> passage.getLinks().stream())
        .anyMatch(passageLink -> passageLink.getReference().equals(link.getReference()));
    if (!linksToPassage && passageToRemove != null) {
      passages.remove(link);
    }
  }

  public List<Link> getBrokenLinks() {
    List<Link> brokenLinks = passages.values().stream()
        .flatMap(passage -> passage.getLinks().stream())
        .filter(link -> {
          Optional<Passage> optPassage = Optional.ofNullable(getPassage(link));
          return !optPassage.isPresent() || !link.getReference().equals(optPassage.get().getTitle());
        })
        .collect(Collectors.toList());
    System.out.println(brokenLinks);
    return brokenLinks;
  }
}