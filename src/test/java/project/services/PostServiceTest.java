package project.services;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import project.dto.PostDto;
import project.dto.requestDto.PostRequestBodyTagsDto;
import project.dto.responseDto.ListResponseDto;
import project.dto.responseDto.ResponseDto;
import project.models.Post;
import project.models.Tag;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(value = {"/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource("/test.properties")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @SneakyThrows
    public void findAllPostsTest(){
        ListResponseDto<PostDto> dto = postService.findAllPosts("Title1", 0, 20);
        assertEquals(dto.getData().size(), 1);
    }

    @Test
    @SneakyThrows
    public void getPostById(){
       Post post =  postService.getPostById(100);
       assertEquals(post.getPostText(), "post text");
    }

    @Test
    @SneakyThrows
    public void editPostById(){
        Post post =  postService.getPostById(100);
        List<String> tags = post.getTagList().stream().map(Tag::getTag).collect(toList());
        PostRequestBodyTagsDto dto = new PostRequestBodyTagsDto("Title1", "new text", tags);
        ResponseDto<PostDto> post2 = postService.editPostById(100, 124213L, dto);
        assertEquals(post2.getData().getPostText(), "new text");
    }

    @Test
    @SneakyThrows
    public void deletePostById(){
        ResponseDto<Integer> dto = postService.deletePostById(100);
        assertEquals(dto.getData().toString(), "100");
    }

    @Test
    @SneakyThrows
    public void getPostDtoById(){
        // можно не тестить, используется в findAllPostsTest
    }

    @Test
    @SneakyThrows
    public void addNewWallPostByAuthorId(){

    }

    @Test
    @SneakyThrows
    public void saveTags(){

    }

    @Test
    @SneakyThrows
    public void findAllByAuthorId(){

    }

    @Test
    @SneakyThrows
    public void getPostsByTitleAndDate(){

    }

    @Test
    @SneakyThrows
    public void deleteAllPostsByAuthorId(){

    }
}
