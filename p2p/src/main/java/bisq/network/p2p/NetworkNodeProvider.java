/*
 * This file is part of Bisq.
 *
 * bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.network.p2p;

import bisq.network.NetworkOptionKeys;
import bisq.network.p2p.network.BridgeAddressProvider;
import bisq.network.p2p.network.LocalhostNetworkNode;
import bisq.network.p2p.network.NetworkNode;
import bisq.network.p2p.network.TorNetworkNode;

import bisq.common.proto.network.NetworkProtoResolver;

import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.inject.Inject;

import java.io.File;

public class NetworkNodeProvider implements Provider<NetworkNode> {

    private final NetworkNode networkNode;

    @Inject
    public NetworkNodeProvider(NetworkProtoResolver networkProtoResolver,
                               BridgeAddressProvider bridgeAddressProvider,
                               @Named(NetworkOptionKeys.USE_LOCALHOST_FOR_P2P) boolean useLocalhostForP2P,
                               @Named(NetworkOptionKeys.MY_ADDRESS) String address,
                               @Named(NetworkOptionKeys.PORT_KEY) int port,
                               @Named(NetworkOptionKeys.TOR_DIR) File torDir) {
        networkNode = useLocalhostForP2P ?
                new LocalhostNetworkNode(address, port, networkProtoResolver) :
                new TorNetworkNode(port, torDir, networkProtoResolver, bridgeAddressProvider);
    }

    @Override
    public NetworkNode get() {
        return networkNode;
    }
}
