package netizen.com.dowhatyougottado.server;

import java.util.LinkedList;
import java.util.List;

import netizen.com.dowhatyougottado.model.Founder;
import netizen.com.dowhatyougottado.model.Task;
import netizen.com.dowhatyougottado.model.TaskList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DoWhatYouGottaDoAPI {

    @POST("createaccount/")
    Call<Founder> createFounder(@Body Founder founder);

    @GET("key/{list_key}")
    Call<LinkedList<Task>> getTaskByListKey(@Path("list_key") String list_key);

    @POST("task")
    Call<Task> addTask(@Body Task newTask);

    @GET("task/done/{task_id}")
    Call<ResponseBody> makeTaskDone(@Path("task_id") long  task_id);

    @GET("task/remove/{task_id}")
    Call<ResponseBody> removeTask(@Path("task_id") long  task_id);

    @POST("setlistkey")
    Call<Founder> pinToBudList(@Body Founder founderWithNewListKey);

    @PUT("setcoordinates")
    Call<ResponseBody> setCoordinates(@Body Founder founderWithcoordinates);

    @POST("getcoordinates")
    Call<LinkedList<Founder>> getCoordinates(@Body Founder founderWithcoordinates);
}
