package article_categorization;

import java.util.List;
import java.util.Map;

// Concrete Strategy for Keyword-based Categorization
public class KeywordCategoryStrategy implements article_categorization.CategoryStrategy {

    private final CategoryManager categoryManager = new CategoryManager();

    @Override
    public String determineCategory(String title, String description, String content) {
        Map<String, List<String>> categoryKeywords = categoryManager.getCategoryKeywords();
        String bestCategory = "Uncategorized";
        int maxMatchCount = 0;

        for (Map.Entry<String, List<String>> entry : categoryKeywords.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();

            int matchCount = 0;
            for (String keyword : keywords) {
                if (title.toLowerCase().contains(keyword.toLowerCase()) ||
                        description.toLowerCase().contains(keyword.toLowerCase()) ||
                        content.toLowerCase().contains(keyword.toLowerCase())) {
                    matchCount++;
                }
            }

            if (matchCount > maxMatchCount) {
                bestCategory = category;
                maxMatchCount = matchCount;
            }
        }
        return bestCategory;
    }
}
