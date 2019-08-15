/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.palindromes;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<Pair<Integer, Integer>, PalindromeGroup> findings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onFindPalindromes(View view) {
        findings.clear();
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView textView = (TextView) findViewById(R.id.textView);
        String text = editText.getText().toString();
        text = text.replaceAll(" ", "");
        text = text.replaceAll("'", "");
        char[] textAsChars = text.toCharArray();
        if (isPalindrome(textAsChars, 0, text.length())) {
            textView.setText(text + " is already a palindrome!");
        } else {
            PalindromeGroup palindromes = breakIntoPalindromes(text.toCharArray(), 0, text.length());
            textView.setText(palindromes.toString());
        }
        return true;
    }

    private boolean isPalindrome(char[] text, int start, int end) {
        end--;

        for (int i = start; i < end; ) {
            if (!(text[start] == text[end])) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }


      /*  private PalindromeGroup breakIntoPalindromes ( char[] text, int start, int end){

            PalindromeGroup bestGroup = null;
            Log.d("breakintopa---->", "Initally--->start--->" + start + "End---->" + end);
            int tempEnd = start + 1;
            System.out.println("temp-end--" + tempEnd);
            if (tempEnd == end) {
                System.out.println("tempend==end---end-" + end);
                bestGroup = new PalindromeGroup(text, start, end);
                Log.d("last one", "" + toString(text, start, end));
                return bestGroup;
            }
            System.out.println("before lopp-->" + tempEnd + "-----end--" + end);
            boolean rs = false;
            while (tempEnd <= end) {
                System.out.println("Calling ispalin---start" + start + "--end-1=" + (tempEnd - 1));
                rs = isPalindrome(text, start, tempEnd);
                System.out.println("isPalindrome(text,start,tempEnd)-->" + rs);
                if (!rs) {
                    System.out.println("not a palin---->" + text + "--start---" + start + "end---" + tempEnd);
                    Log.d("not palidrome", "" + toString(text, start, tempEnd));
                    bestGroup = new PalindromeGroup(text, start, tempEnd - 1);
                    Log.d("BestGroup", "" + toString(text, start, tempEnd - 1));
                    System.out.println("breaking");
                    break;
                }
                System.out.println("tempend before inc--->" + tempEnd);
                tempEnd++;
                System.out.println("tempend after inc--->" + tempEnd);
            }
            System.out.println("out of while--->tempend--->" + tempEnd + "---end---" + end);
            Log.d("Left Group" + (tempEnd - 1) + " " + end, "" + toString(text, tempEnd - 1, end));
            bestGroup.append(breakIntoPalindromes(text, tempEnd - 1, end));
            return bestGroup;
        }
*/

    public String toString(char[] text, int start, int end) {
        String s = "";
        for (int i = start; i < end; i++)
            s += text[i];
        return s;
    }


    // TODO RECURSIVE
   /* private PalindromeGroup breakIntoPalindromes(char[] text, int start, int end) {
        PalindromeGroup bestGroup = null;
        int minNumOfPalindromes = Integer.MAX_VALUE;
        int tempEnd = start + 1;
        while (tempEnd <= end) {
            System.out.println("Entering while...iteration.....");
            if (isPalindrome(text, start, tempEnd)) {
                System.out.println("is palindrome true--->text" + text + "--start---" + start + "--tempend-->" + tempEnd);
                PalindromeGroup newGroup;
                newGroup = new PalindromeGroup(text, start, tempEnd);
                newGroup.append(breakIntoPalindromes(text, tempEnd, end));
                Log.d("string", "" + newGroup.strings.toString());
                if (newGroup.length() < minNumOfPalindromes) {
                    bestGroup = newGroup;
                    minNumOfPalindromes = bestGroup.length();
                    Log.d("Size" + bestGroup.length(), "" + minNumOfPalindromes);
                }
            }
            tempEnd++;
        }

        return bestGroup;
    }
}*/
    private PalindromeGroup breakIntoPalindromes(char[] text, int start, int end) {
        PalindromeGroup bestGroup = null;
        int minNumOfPalindromes = Integer.MAX_VALUE;

        System.out.println("minNumofPalindromes--->" + minNumOfPalindromes);
        int tempEnd = start + 1;
        System.out.println("tempendafterinc---<>" + tempEnd);
        while (tempEnd <= end) {
            System.out.println("Entering while/..................");
            Pair<Integer, Integer> newPair = new Pair<>(start, tempEnd);
            PalindromeGroup newGroup;
            boolean res = isPalindrome(text, start, tempEnd);
            System.out.println("calling is palin--->text" + text + " start---->" + start + " tempend--->" + tempEnd);
            if (res) {
                System.out.println("returned-->" + res);
                if (findings.containsKey(newPair)) {
                    System.out.println("Yes contains key-->" + newPair.first + "value--->" + newPair.second);
                    newGroup = findings.get(newPair);
                } else {
                    System.out.println("Else.......");
                    newGroup = new PalindromeGroup(text, start, tempEnd);
                    newGroup.append(breakIntoPalindromes(text, tempEnd, end));
                    System.out.println("Nokeyfound so putting--->" + newPair.first + " New pair second " + newPair.second);
                    findings.put(newPair, newGroup);
                }
                System.out.println("new group.length=" + newGroup.length() + " minNumofPalindromes-->" + minNumOfPalindromes);
                if (newGroup.length() < minNumOfPalindromes) {
                    bestGroup = newGroup;
                    minNumOfPalindromes = bestGroup.length();
                }
            }
            System.out.println("tempendatendbefore=" + tempEnd);
            tempEnd++;
            System.out.println("tempendatend=" + tempEnd);
        }
        return bestGroup;
    }
}