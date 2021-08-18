# Rubber Tree

1. Run client.
2. Create a new world (creative world).
3. Fly around to find a rubber tree.
4. Dig all the logs and check whether the leaves break automatically and drop rubber tree sapling.
   (You might be very unlucky that rubber tree sapling don't drop but in fact the mod works well, so you should replace this several times before report a bug).
5. Plant the sapling and wait it growing. (You can set randomTick speed to make it grow faster, but don't use bone meal, which force to judge the growing, we need to check the growing happens on random tick).
6. Repeat step 4 once with the new grown tree.
7. Use a sword on a natural grown rubber log. It should use another model.
8. Put an empty bowl into it (by "using" the bowl on the log).
9. Try putting another empty bowl into it (by "using" the bowl on the log). This should fail.
10. Take the bowl out (by "using" the log with empty hand).
11. Repeat 8, and wait for 100 ticks (around 5s). Check whether a bowl of rubber is ejected.
12. Repeat 8 to 11.
13. Repeat 8 and exit the game at once.
14. Reopen the game. Wait for 100 ticks (around 5s). Check whether a bowl of rubber is ejected.
15. Try putting a bowl into a non-sword used rubber log. This operation should fail.
16. Put a rubber log manually, use a sword on it. It should not use another model.
