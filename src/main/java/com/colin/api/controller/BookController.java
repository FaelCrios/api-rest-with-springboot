package com.colin.api.controller;

import com.colin.api.data.vo.v1.BooksVO;
import com.colin.api.services.BookServices;
import com.colin.api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/v1")
@Tag(name="Books", description = "Endpoint for managing books")
public class BookController {

    @Autowired
    private BookServices services;

    @GetMapping(produces = { MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_YML
    })
    @Operation(summary = "Finds all the books", description = "Finds all the books in our DataBase",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = BooksVO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Nod Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)


            }
    )
    public List<BooksVO> findAll(){
        return services.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML  })
    @Operation(summary = "Finding one Book", description = "Finding one Book by ID",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(implementation = BooksVO.class)
                            )
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Nod Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)


            }
    )
    public BooksVO findById(@PathVariable(value = "id") Long id){
        return services.findById(id);
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML  })
    @Operation(summary = "Adds a Book",
            description = "Adds a new Book by passing in a JSON, XML or YML representation of the Book",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(implementation = BooksVO.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)


            }
    )
    public BooksVO create(@RequestBody BooksVO booksVO){
        return services.createBook(booksVO);
    }

    @PutMapping(
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML  })
    @Operation(summary = "Updates a Book", description = "Updates a new Book by passing in a JSON, XML or YML representation of the Book",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(implementation = BooksVO.class)
                            )
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)


            }
    )
    public BooksVO update(@RequestBody BooksVO book){
        return services.updateBook(book);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a Book", description = "Deletes a Book by passing the ID",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)


            }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }



}
