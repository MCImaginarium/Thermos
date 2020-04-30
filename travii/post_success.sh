cp build/distributions/Thermos*server.jar $HOME/release/
cd build/bundle/

rm -rf libraries/pw.prok/Thermos
zip -r libraries.zip libraries
cp libraries.zip $HOME/release