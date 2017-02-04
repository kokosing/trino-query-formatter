/*
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
package rocks.prestodb.query.formatatter;

import com.facebook.presto.sql.parser.SqlParser;
import com.facebook.presto.sql.parser.StatementSplitter;
import com.google.common.collect.ImmutableSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static com.facebook.presto.sql.SqlFormatter.formatSql;

public class Formatter
{
    private static final SqlParser SQL_PARSER = new SqlParser();

    public static void main(String[] args)
            throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            StringBuilder buffer = new StringBuilder();
            while (reader.ready()) {
                buffer.append(reader.readLine() + "\n");
                String sql = buffer.toString();
                StatementSplitter splitter = new StatementSplitter(sql, ImmutableSet.of(";", "\\G"));
                for (StatementSplitter.Statement split : splitter.getCompleteStatements()) {
                    System.out.println(formatSql(SQL_PARSER.createStatement(split.statement()), Optional.empty()) + ";");
                }

                // replace buffer with trailing partial statement
                buffer = new StringBuilder();
                String partial = splitter.getPartialStatement();
                if (!partial.isEmpty()) {
                    buffer.append(partial).append('\n');
                }
            }
        }
    }
}
