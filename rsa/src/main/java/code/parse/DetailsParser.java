package code.parse;

import code.parse.csvtype.CsvDetail;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readString;

/**
 * readDetNames() - получить из файла список имен деталей (тип файла CsvDetail)
 * writeDetNames() - записать в файл список деталей (тип файла CsvDetail)
 * readDetOnlyNames() - получить из файла список имен деталей (тип файла - только имена)
 * writeDetOnlyNames() - записать в файл список имен деталей (тип файла - только имена)
 */
public class DetailsParser {

    public static List<String> readDetNames(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDetail.class)
                .withColumnSeparator(';').withSkipFirstDataRow(true);
        MappingIterator<CsvDetail> iterator = mapper
                .readerFor(CsvDetail.class)
                .with(schema)
                .readValues(myReader);
        List<CsvDetail> elements = iterator.readAll();

        return elements.stream()
                .map(x -> x.getName())
                .sorted()
                .collect(Collectors.toList());
    }

    public static void writeDetNames(String fileName, List<CsvDetail> elements ) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDetail.class)
                .withColumnSeparator(';')
                .withHeader();
        ObjectWriter writer = mapper.writer(schema);
        writer.writeValue(new FileWriter(fileName, StandardCharsets.UTF_8),elements);
    }

    public static List<String> readDetOnlyNames(String fileName) throws IOException {
        Reader myReader = new FileReader(fileName, StandardCharsets.UTF_8);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(String.class)
                .withColumnSeparator('\n')
                .withSkipFirstDataRow(true);
        MappingIterator<String> iterator = mapper
                .readerFor(String.class)
                .with(schema)
                .with(CsvParser.Feature.SKIP_EMPTY_LINES)
                .readValues(myReader);
        List<String> list = iterator.readAll();

        return list.stream()
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.toList());
    }

    public static void writeDetOnlyNames(String fileName, List<String> elements ) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("name").build()
                .withHeader().withColumnSeparator('\n');
        ObjectWriter writer = mapper.writer(schema);
        writer.writeValue(new FileWriter(fileName, StandardCharsets.UTF_8), elements);
    }

}
