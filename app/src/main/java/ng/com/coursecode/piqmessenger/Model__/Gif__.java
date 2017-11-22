package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by harro on 31/10/2017.
 */

public class Gif__ {
    /*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Gif.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    @SerializedName("weburl")
    @Expose
    private String weburl;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("next")
    @Expose
    private String next;

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }


/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Gif_.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Gif_ {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Loopedmp4.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Loopedmp4 {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("duration")
        @Expose
        private Double duration;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Medium.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Medium {

        @SerializedName("nanomp4")
        @Expose
        private Nanomp4 nanomp4;
        @SerializedName("nanowebm")
        @Expose
        private Nanowebm nanowebm;
        @SerializedName("tinygif")
        @Expose
        private Tinygif tinygif;
        @SerializedName("tinymp4")
        @Expose
        private Tinymp4 tinymp4;
        @SerializedName("tinywebm")
        @Expose
        private Tinywebm tinywebm;
        @SerializedName("webm")
        @Expose
        private Webm webm;
        @SerializedName("gif")
        @Expose
        private Gif_ gif;
        @SerializedName("mp4")
        @Expose
        private Mp4 mp4;
        @SerializedName("nanogif")
        @Expose
        private Nanogif nanogif;
        @SerializedName("mediumgif")
        @Expose
        private Mediumgif mediumgif;
        @SerializedName("loopedmp4")
        @Expose
        private Loopedmp4 loopedmp4;

        public Nanomp4 getNanomp4() {
            return nanomp4;
        }

        public void setNanomp4(Nanomp4 nanomp4) {
            this.nanomp4 = nanomp4;
        }

        public Nanowebm getNanowebm() {
            return nanowebm;
        }

        public void setNanowebm(Nanowebm nanowebm) {
            this.nanowebm = nanowebm;
        }

        public Tinygif getTinygif() {
            return tinygif;
        }

        public void setTinygif(Tinygif tinygif) {
            this.tinygif = tinygif;
        }

        public Tinymp4 getTinymp4() {
            return tinymp4;
        }

        public void setTinymp4(Tinymp4 tinymp4) {
            this.tinymp4 = tinymp4;
        }

        public Tinywebm getTinywebm() {
            return tinywebm;
        }

        public void setTinywebm(Tinywebm tinywebm) {
            this.tinywebm = tinywebm;
        }

        public Webm getWebm() {
            return webm;
        }

        public void setWebm(Webm webm) {
            this.webm = webm;
        }

        public Gif_ getGif() {
            return gif;
        }

        public void setGif(Gif_ gif) {
            this.gif = gif;
        }

        public Mp4 getMp4() {
            return mp4;
        }

        public void setMp4(Mp4 mp4) {
            this.mp4 = mp4;
        }

        public Nanogif getNanogif() {
            return nanogif;
        }

        public void setNanogif(Nanogif nanogif) {
            this.nanogif = nanogif;
        }

        public Mediumgif getMediumgif() {
            return mediumgif;
        }

        public void setMediumgif(Mediumgif mediumgif) {
            this.mediumgif = mediumgif;
        }

        public Loopedmp4 getLoopedmp4() {
            return loopedmp4;
        }

        public void setLoopedmp4(Loopedmp4 loopedmp4) {
            this.loopedmp4 = loopedmp4;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Mediumgif.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Mediumgif {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Mp4.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Mp4 {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("duration")
        @Expose
        private Double duration;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Nanogif.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Nanogif {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Nanomp4.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Nanomp4 {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("duration")
        @Expose
        private Double duration;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Nanowebm.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Nanowebm {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Result.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Result {

        @SerializedName("hascaption")
        @Expose
        private Boolean hascaption;
        @SerializedName("created")
        @Expose
        private Double created;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;
        @SerializedName("tags")
        @Expose
        private List<Object> tags = null;
        @SerializedName("shares")
        @Expose
        private Integer shares;
        @SerializedName("itemurl")
        @Expose
        private String itemurl;
        @SerializedName("composite")
        @Expose
        private Object composite;
        @SerializedName("hasaudio")
        @Expose
        private Boolean hasaudio;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("id")
        @Expose
        private String id;

        public Boolean getHascaption() {
            return hascaption;
        }

        public void setHascaption(Boolean hascaption) {
            this.hascaption = hascaption;
        }

        public Double getCreated() {
            return created;
        }

        public void setCreated(Double created) {
            this.created = created;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Medium> getMedia() {
            return media;
        }

        public void setMedia(List<Medium> media) {
            this.media = media;
        }

        public List<Object> getTags() {
            return tags;
        }

        public void setTags(List<Object> tags) {
            this.tags = tags;
        }

        public Integer getShares() {
            return shares;
        }

        public void setShares(Integer shares) {
            this.shares = shares;
        }

        public String getItemurl() {
            return itemurl;
        }

        public void setItemurl(String itemurl) {
            this.itemurl = itemurl;
        }

        public Object getComposite() {
            return composite;
        }

        public void setComposite(Object composite) {
            this.composite = composite;
        }

        public Boolean getHasaudio() {
            return hasaudio;
        }

        public void setHasaudio(Boolean hasaudio) {
            this.hasaudio = hasaudio;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Tinygif.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Tinygif {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Tinymp4.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Tinymp4 {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("duration")
        @Expose
        private Double duration;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public Double getDuration() {
            return duration;
        }

        public void setDuration(Double duration) {
            this.duration = duration;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Tinywebm.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Tinywebm {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
/*-----------------------------------ng.com.coursecode.piqmessenger.Model__.Webm.java-----------------------------------

            package ng.com.coursecode.piqmessenger.Model__;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

    public class Webm {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("dims")
        @Expose
        private List<Integer> dims = null;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("size")
        @Expose
        private Integer size;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Integer> getDims() {
            return dims;
        }

        public void setDims(List<Integer> dims) {
            this.dims = dims;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }
}
