%% Copyright
-module(test).
-author("link").

-include_lib("eunit/include/eunit.hrl").

simple_test() ->
  storehouse:start(),
  Shelf = storehouse:create_shelf(bookshelf),
  io:format("Shelf: ~w~n", [Shelf]),

  ?assert(Shelf =:= []).
