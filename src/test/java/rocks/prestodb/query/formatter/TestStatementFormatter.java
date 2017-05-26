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
package rocks.prestodb.query.formatter;

import com.facebook.presto.sql.parser.SqlParser;
import com.facebook.presto.sql.tree.Statement;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static rocks.prestodb.query.formatter.StatementFormatter.formatSql;

public class TestStatementFormatter {
    private static final SqlParser SQL_PARSER = new SqlParser();

    @Test
    public void test() {
        queries().forEach(this::testQuery);
    }

    private void testQuery(String query) {
        System.out.println(">>>> Testing: " + query);
        Statement statement = SQL_PARSER.createStatement(query);
        String formmatted = formatSql(statement);
        System.out.println(formmatted);
        assertEquals(formmatted, formatSql(SQL_PARSER.createStatement(formmatted)));
    }

    @Test
    public void testQuery() {
        testQuery(
                "SELECT (1 = ANY(SELECT 1)) "
        );
        testQuery(
                "SELECT (1 = ANY(SELECT 1)) IN (SELECT TRUE)"
        );
    }

    private Stream<String> queries() {
        return new BufferedReader(new InputStreamReader(TestStatementFormatter.class.getResourceAsStream("/queries.txt"))).lines();
    }
}