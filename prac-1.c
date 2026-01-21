#include <stdio.h>

int main() {
    char input[50];
    int i = 0;

    scanf("%s", input);
    
    while (input[i] == 'a') {
        i++;
    }

    if (input[i] == 'b' && input[i+1] == 'b' && input[i+2] == '\0') {
        printf("Valid String");
    } else {
        printf("Invalid String");
    }

    return 0;
}