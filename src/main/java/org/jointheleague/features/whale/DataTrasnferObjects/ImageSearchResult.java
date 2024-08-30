
package org.jointheleague.features.whale.DataTrasnferObjects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ImageSearchResult {

    @SerializedName("search_metadata")
    @Expose
    private SearchMetadata searchMetadata;
    @SerializedName("search_parameters")
    @Expose
    private SearchParameters searchParameters;
    @SerializedName("search_information")
    @Expose
    private SearchInformation searchInformation;
    @SerializedName("suggested_searches")
    @Expose
    private List<SuggestedSearch> suggestedSearches = new ArrayList<SuggestedSearch>();
    @SerializedName("images_results")
    @Expose
    private List<ImagesResult> imagesResults = new ArrayList<ImagesResult>();
    @SerializedName("related_searches")
    @Expose
    private List<RelatedSearch> relatedSearches = new ArrayList<RelatedSearch>();
    @SerializedName("serpapi_pagination")
    @Expose
    private SerpapiPagination serpapiPagination;

    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public SearchInformation getSearchInformation() {
        return searchInformation;
    }

    public void setSearchInformation(SearchInformation searchInformation) {
        this.searchInformation = searchInformation;
    }

    public List<SuggestedSearch> getSuggestedSearches() {
        return suggestedSearches;
    }

    public void setSuggestedSearches(List<SuggestedSearch> suggestedSearches) {
        this.suggestedSearches = suggestedSearches;
    }

    public List<ImagesResult> getImagesResults() {
        return imagesResults;
    }

    public void setImagesResults(List<ImagesResult> imagesResults) {
        this.imagesResults = imagesResults;
    }

    public List<RelatedSearch> getRelatedSearches() {
        return relatedSearches;
    }

    public void setRelatedSearches(List<RelatedSearch> relatedSearches) {
        this.relatedSearches = relatedSearches;
    }

    public SerpapiPagination getSerpapiPagination() {
        return serpapiPagination;
    }

    public void setSerpapiPagination(SerpapiPagination serpapiPagination) {
        this.serpapiPagination = serpapiPagination;
    }

}
