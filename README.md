# Minimal-weight-in-a-weighted-graph
Minimal weight in a weighted graph

-----------------------------------------------------------------------------------------------------------------
Assignment C
Objective:
Design a program that finds the path with the minimal weight in a weighted graph.
Design Specification:
The graph nodes are labeled with string tags and have an associated floating-point weight value;
The program reads the graph structure from a text file;
The program takes two node tags as command-line arguments and calculates the path with a 
minimal weight between two nodes if such a path exists; the weight of the path is defined as a sum 
of individual weights of each edge in the path.
The program dumps the path as a series of tags and associated weights;
The graph is represented as a text file with uniform lines with three elements. The elements specify 
a graph’s edge:
- A start node tag, an arbitrary character string
- The associated weight, represented as a floating-point number
- An end node tag, an arbitrary character string
- The elements in a line are separated by one or more blank symbols;
- A tag should be surrounded with double quotes if it contains one or more blanks;
- A double quote symbol is not allowed as a character in a tag.
Example:
apple 0.7 pear
apple 0.2 pumpkin
pumpkin 0.3 orange
pear 0.1 orange
