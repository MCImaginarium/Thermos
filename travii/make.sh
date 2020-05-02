cho ""
echo "==================== Debug Info ==================="
echo "                Cleaning up Directories            "
echo "==================================================="
echo ""
mkdir -p $HOME/release
echo "Deleting all files in the releaces folder.."
rm -rf $HOME/release/*
echo ""
echo "================== Build Thermos =================="
echo "              Build Forge 1614 Version             "
echo "==================================================="
echo ""
echo "Building Server version for forge build 1614"
./gradlew --parallel -PofficialBuild -PforgeBuildNumber='1614' clean setupCauldron installbundle
echo "Done!"
echo ""
echo "Moving 1614 server jar to the releace folder..."
cp build/distributions/Thermos*server.jar $HOME/release/
echo "Done!"
echo ""
echo "================== Build Thermos =================="
echo "              Compiling Library Files              "
echo "==================================================="
echo ""
if [ ! -f $HOME/release/libraries.zip ]; then
	echo "Building library zip archive..."
	cd build/bundle/
	rm -rf libraries/pw/prok/Thermos
	zip -r libraries.zip libraries
	echo "Done!"
	echo ""
	echo "Moving library zip archive to release folder..."
	cp libraries.zip $HOME/release
	cd ../..
	echo "Done!"
else 
    echo "Library files alredy exist in target foler, nothing further to do."
fi
echo "Done!"
echo ""
echo "================== Build Thermos =================="
echo "              Build Forge 1558 Version             "
echo "==================================================="
echo ""
echo "Setting up Enviroment for forge build 1558"
./gradlew --parallel -PofficialBuild -PforgeBuildNumber='1558'  setForgeBuildNumber
cd forge
git checkout aa8eaf2b286e809146b7faf4e59ce801a40eab9b
cd ..
echo ""
echo "Building Server version for forge build 1558"
./gradlew --parallel -PofficialBuild -PforgeBuildNumber='1558' clean setupCauldron jar
echo "Done!"
echo ""
echo "Moving 1558 server jar to the releace folder..."
cp build/distributions/Thermos*server.jar $HOME/release/
echo "Done!"
echo ""
echo "================== Build Thermos =================="
echo "                 All Tasks Completed               "
echo "==================================================="
echo ""

