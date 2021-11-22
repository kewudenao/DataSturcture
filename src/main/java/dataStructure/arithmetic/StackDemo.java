package dataStructure.arithmetic;

import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        System.out.println(isValid("()"));
    }
     static    public boolean isValid(String s) {
            char[] newArr =  s.toCharArray();
            Stack stack = new Stack();
            for(int i=0;i<newArr.length;i++){
                if(newArr[i]=='(' ||newArr[i]=='{'||newArr[i]=='[' ){
                    stack.push(newArr[i]);
                }else{

                    if(newArr[i]==')'&& (Character) stack.peek()=='(' )
                    {

                        stack.pop();
                    }else if(newArr[i]=='}' && (Character) stack.peek()=='{') {
                        stack.pop();

                    }else if (newArr[i]==']'&&(Character) stack.peek()=='['){
                        stack.pop();
                    }else{
                        return false;
                    }
                }

            }
            return stack.empty();
        }

}
