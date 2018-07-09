package net.ukr.shyevhen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CounterLetterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msg = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Result</title><style>@import url('style/tablestyle.css');</style></head><body><table>%s</table></body></html>";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String text = req.getParameter("text");
		text = text.replaceAll("[\\W\\d]", "");
		text = text.toUpperCase();
		char[] letters = text.toCharArray();
		Map<Character, Integer> letterCounter = new HashMap<>();
		for (char c : letters) {
			if (letterCounter.containsKey(c)) {
				letterCounter.put(c, letterCounter.get(c) + 1);
			} else {
				letterCounter.put(c, 1);
			}
		}
		letters = new char[letterCounter.size()];
		int[] count = new int[letters.length];
		int n = 0;
		for (char c : letterCounter.keySet()) {
			letters[n] = c;
			count[n++] = letterCounter.get(c);
		}
		sortedMeth(letters, count);
		StringBuilder sb = new StringBuilder("<tr><th>Letter<th>Counter");
		for (int i = 0; i < count.length; i++) {
			sb.append("<tr><td class='letter'>" + letters[i] + "<td>" + count[i]);
		}
		PrintWriter pw = resp.getWriter();
		pw.print(String.format(msg, sb.toString()));
	}

	private void sortedMeth(char[] letters, int[] count) {
		char temp1;
		int temp2 = 0;
		for (int i = 0; i < count.length; i++) {
			for (int j = i + 1; j < count.length; j++) {
				if (count[i] < count[j]) {
					temp2 = count[i];
					count[i] = count[j];
					count[j] = temp2;
					temp1 = letters[i];
					letters[i] = letters[j];
					letters[j] = temp1;
				}
			}
		}
	}

}
