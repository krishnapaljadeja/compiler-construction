#include<bits/stdc++.h>
using namespace std;

    unordered_set<string> keywords = {
        "int", "float", "double", "char", "void",
        "if", "else", "while", "for", "return",
        "struct", "long","main"};
    unordered_set<string> symbolTable;
    string operators = "+-*/%=<>!";
    string punctuation = "();{},[]";

    bool isKeyword(const string &s) {
    return keywords.count(s);
}

bool isOperator(char c) {
    return operators.find(c) != string::npos;
}

bool isPunctuation(char c) {
    return punctuation.find(c) != string::npos;
}

int main() {
    ifstream file("input.c");
    if (!file) {
        cout << "Error opening file\n";
        return 1;
    }

    char ch;
    int line = 1;
    vector<string> errors;

    cout << "\nTOKENS\n";

    while (file.get(ch)) {

        // Line count
        if (ch == '\n') {
            line++;
            continue;
        }

        // Ignore whitespace
        if (isspace(ch))
            continue;

        // Remove single-line comments
        if (ch == '/' && file.peek() == '/') {
            while (file.get(ch) && ch != '\n');
            line++;
            continue;
        }

        // Remove multi-line comments
        if (ch == '/' && file.peek() == '*') {
            file.get();
            while (file.get(ch)) {
                if (ch == '\n') line++;
                if (ch == '*' && file.peek() == '/') {
                    file.get();
                    break;
                }
            }
            continue;
        }

        // Identifier or keyword
        if (isalpha(ch) || ch == '_') {
            string word;
            word += ch;

            while (isalnum(file.peek()) || file.peek() == '_') {
                file.get(ch);
                word += ch;
            }

            if (isKeyword(word))
                cout << "Keyword: " << word << endl;
            else {
                cout << "Identifier: " << word << endl;
                symbolTable.insert(word);
            }
        }

        // Numeric constant or invalid lexeme
        else if (isdigit(ch)) {
            string num;
            num += ch;

            while (isalnum(file.peek()) || file.peek() == '.') {
                file.get(ch);
                num += ch;
            }

            if (isalpha(num.back())) {
                errors.push_back("Line " + to_string(line) +
                                 " : " + num + " invalid lexeme");
            } else {
                cout << "Constant: " << num << endl;
            }
        }

        // Character constant
        else if (ch == '\'') {
            string c;
            c += ch;
            file.get(ch);
            c += ch;
            file.get(ch); // closing '
            c += ch;
            cout << "Constant: " << c << endl;
        }

        // Operators
        else if (isOperator(ch)) {
            cout << "Operator: " << ch << endl;
        }

        // Punctuation
        else if (isPunctuation(ch)) {
            cout << "Punctuation: " << ch << endl;
        }
    }

    // Print lexical errors
    cout << "\nLEXICAL ERRORS\n";
    if (errors.empty())
        cout << "None\n";
    else
        for (auto &e : errors)
            cout << e << endl;

    // Print symbol table
    cout << "\nSYMBOL TABLE ENTRIES\n";
    int i = 1;
    for (auto &id : symbolTable) {
        cout << i++ << ") " << id << endl;
    }

    file.close();
    return 0;
}
