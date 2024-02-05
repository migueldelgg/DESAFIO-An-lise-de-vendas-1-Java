package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.next();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");

                list.add(new Sale(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));

                line = br.readLine();
            }

            Comparator<Sale> comp = (s1, s2) -> Double.compare(s1.averagePrice(), s2.averagePrice());

            List<Sale> fiveSales = list.stream()
                    .filter(s -> s.getYear() == 2016)
                    .sorted(comp.reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            fiveSales.forEach(System.out::println);

            double saleOfLogan = list.stream()
                    .filter(s -> s.getSeller().equals("Logan"))
                    .filter(sale -> sale.getMonth() == 1 || sale.getMonth() == 7)
                    .map(sale -> sale.getTotal())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.println();
            System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = "
                    + String.format("%.2f", saleOfLogan));

        } catch (IOException E) {
            System.out.println("Error: " + E.getMessage());
        } finally {
            sc.close();
        }
    }
}
