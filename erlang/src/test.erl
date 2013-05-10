%% Copyright
-module(test).
-author("link").

-include_lib("eunit/include/eunit.hrl").

storehouse_test() ->
  storehouse:start(),
  Shelf = storehouse:create_shelf(bookshelf),
  io:format("Shelf: ~w~n", [Shelf]),

  ?assert(Shelf =:= []).

new_shelf_test() ->
  Shelf = shelves:new_shelf(3),
  ?assert(shelves:products(Shelf) =:= []),
  ?assert(shelves:capacity(Shelf) =:= 3).

put_in_products_test() ->
  Shelf = shelves:new_shelf(3),
  Shelf1 = shelves:put_in(Shelf, book),
  Shelf2 = shelves:put_in(Shelf1, magazine),
  ?assertEqual([magazine, book], shelves:products(Shelf2)).

put_in_over_capacity_test() ->
  Shelf = shelves:new_shelf(1),
  Shelf1 = shelves:put_in(Shelf, book),
  ?assertException(throw, {over_capacity}, shelves:put_in(Shelf1, magazine)).

take_out_products_test() ->
  Shelf = shelves:new_shelf(3),
  Shelf1 = shelves:put_in(Shelf, book),
  Shelf2 = shelves:put_in(Shelf1, magazine),
  Shelf3 = shelves:take_out(Shelf2, magazine),
  Shelf3 = shelves:take_out(Shelf3, unknown),
  ?assertEqual([book], shelves:products(Shelf3)).

shelf_in_process_test() ->
  ShelfPid = shelves:start_new_shelf(3),
  ShelfPid ! {self(), {put_in, book}},
  receive
    {ShelfPid, Msg} ->
      ?assertEqual([book], Msg)
  end,
  ShelfPid ! {self(), {put_in, magazine}},
  receive
    {ShelfPid, Msg2} ->
      ?assertEqual([magazine, book], Msg2)
  end,
  ShelfPid ! {self(), stop}.
%%   ?assert(false).
